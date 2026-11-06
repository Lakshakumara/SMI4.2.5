# BMI Simulator — Stepwise Upgrade Plan (offline reference)

> Started: 2026-11-05 · Branch: `upgrade/android16` · Base: `main` @ e23f9e9
> Distribution channel: **Dropbox sideload** (Play account banned — Play Console
> paperwork steps are optional/deferred to Step 9)

---

## Goal

Upgrade the app so it:
1. Builds on modern tooling (AGP 8 / Gradle 8.11 / JDK 17+)
2. Does not crash on Android 12+ devices
3. Works correctly when targeting Android 16 (API 36)
4. Uses modern storage/notification/background APIs
5. Keeps the **Dropbox APK updater** as the official release channel

**Rule for Steps 1–2: no feature/logic changes.** Logic changes only where the
platform forces them (and they are listed explicitly per step).

---

## Step 1 — Build toolchain only ✅ DONE (2026-11-06)

**Goal:** project builds on modern tooling, app behaves exactly the same.

### Changes made (5 build files, 0 Java changes)

| File | Change |
|---|---|
| `gradle/wrapper/gradle-wrapper.properties` | Gradle `8.0` → `8.11.1` |
| `build.gradle` (root) | AGP `7.4.2` → `8.9.2`; removed unused `ext{compose_version, agp_version}`; `rootProject.buildDir` → `layout.buildDirectory` |
| `gradle.properties` | Removed `android.disableAutomaticComponentCreation`, `android.enableJetifier`, `android.debug.obsoleteApi`; `-Xmx1536M` → `2048M`; **added `android.nonFinalResIds=false`** |
| `app/build.gradle` | `compileSdk` → 36 (both places); `minSdkVersion` 19 → 21; **targetSdk stays 34**; versionCode `4254 → 4255`, versionName `4.2.5.4 → 4.2.5.5`; removed `multiDexEnabled` + `androidx.multidex` dep; dropped `buildToolsVersion`; **added `buildFeatures { buildConfig true }`** |
| `MPChartLib/build.gradle` | `compileSdk` 36 / `minSdk` 21; targetSdk stays 34; dropped `buildToolsVersion` |

### AGP 8 gotchas that were hit (and how they were fixed)

1. **`cannot find symbol: BuildConfig`** — AGP 8 disables BuildConfig
   generation by default. Fix: `buildFeatures { buildConfig true }` in
   `app/build.gradle`. (A manual comment-out workaround in `Start.java` was
   reverted to preserve original `!BuildConfig.DEBUG` logic.)
2. **42 × `constant expression required` on `case R.id.*:`** — AGP 8 makes
   `R` fields non-final by default, breaking switch/case. Fix:
   `android.nonFinalResIds=false` in `gradle.properties` (zero code changes).
   *Alternative for a later step: convert switches to if/else, then remove the flag.*
3. **`JAVA_HOME` not set** on this machine — build used
   `C:\Program Files\Android\Android Studio\jbr` (JDK 21). Set it permanently:
   `setx JAVA_HOME "C:\Program Files\Android\Android Studio\jbr"`

### Verification

```
gradlew assembleDebug        → BUILD SUCCESSFUL
app-debug.apk 13.3 MB, versionCode 4255, versionName 4.2.5.5
git diff → only build files changed (0 Java files)
```

---

## Step 2 — OS crash fixes (minimal code, no behavior change)

These are **bugs on Android 12+/14+ devices today**, independent of Play:

1. **`PendingIntent` mutability crash** — `app/src/main/java/sss/yml/smi/service/Notify.java`
   lines ~106, 120, 139, 207: flag `0` → add `PendingIntent.FLAG_IMMUTABLE`
   (crashes with `IllegalArgumentException` on Android 12+ when posting notifications)
2. **Missing manifest entries** — declare in `app/src/main/AndroidManifest.xml`:
   - `sss.yml.smi.util.NortificationActivity` (notification tap target — currently `ActivityNotFoundException`)
   - `sss.yml.smi.service.ReplyReceive` (RemoteInput broadcast receiver)
3. **Foreground service type** — `Notify` service:
   `android:foregroundServiceType="dataSync"` (+ `FOREGROUND_SERVICE_TYPE_DATA_SYNC`
   is covered by the `FOREGROUND_SERVICE` permission merged from libs; add
   `<uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>` explicitly if missing)
4. **Kill-switch removal** — `Home/Start.java` ~line 162:
   `c1.set(2024, 11, 31)` fires `killProcess()` after 2024-12-31 → app
   self-bricks. Remove the forced-update/kill block (keep normal "update
   available" prompt pointing to Dropbox/Play URL if desired).
   *Note: `origin/2026` branch only extended it to 2030 — removal is better.*

**Verify:** `gradlew assembleDebug`, install on Android 12+ device, trigger a
Firestore news notification, tap it, reboot device.

---

## Step 3 — Raise `targetSdk 34 → 36` + forced behavior changes

1. `targetSdkVersion 34 → 36` in `app/build.gradle` + `MPChartLib/build.gradle`
2. **Predictive back** (API 36: `onBackPressed()` silently ignored):
   - quick: `android:enableOnBackInvokedCallback="false"` in `<application>` (1 line)
   - proper (recommended): migrate `chdr/ChartShow.java` `onBackPressed()` →
     `OnBackPressedCallback`; audit other back handling
3. **Edge-to-edge** (no opt-out on API 36): add `WindowInsetsCompat` padding to
   activities/fragments — test chart screens, dialogs, WebView home, SMIData entry
4. **`POST_NOTIFICATIONS` runtime request** (API 33+): request in `Start` flow
   before `Notify` posts anything (`ActivityResultLauncher`)
5. **Backup rules**: keep `android:fullBackupContent`, add
   `android:dataExtractionRules` (API 31+) pointing to new
   `res/xml/data_extraction_rules.xml`

**Verify:** run on Android 16 emulator + Android 15 device; back gesture shows
system animation; no content under status bar; notifications appear after grant.

---

## Step 4 — Storage modernization (needed on Android 10+ anyway)

1. DB backup/restore — `ui/setting/Settings.java` uses
   `Environment.getExternalStorageDirectory()/BMIS/*.sss` → SAF
   (`ACTION_CREATE_DOCUMENT` / `ACTION_OPEN_DOCUMENT`) — works on all APIs, no permission
2. Chart PNG export — `chdr/BFA_5_19.java`, `chdr/HFA_5_19.java`,
   `MPChartLib .../Chart.java saveToGallery()` use `/DCIM/BMISimulator/` →
   `MediaStore.Downloads/Images` API (or SAF)
3. `READ_EXTERNAL_STORAGE` → add `android:maxSdkVersion="32"`; check merged
   `WRITE_EXTERNAL_STORAGE` from MPChartLib manifest — scope it too

**Verify:** backup → restore round-trip on Android 13+ device; chart save
appears in Gallery/Files.

---

## Step 5 — Notifications & background

1. `service/NotificationReceiver.java` (~line 28): `context.startService()` on
   `BOOT_COMPLETED` → background-start restricted on API 26+; options:
   - `ContextCompat.startForegroundService()` + FGS type (from Step 2)
   - **Recommended:** replace boot receiver + persistent Firestore listener
     service with **FCM push** (fewer moving parts, better battery, survives
     boot restrictions)
2. Android 15+: `BOOT_COMPLETED` no longer delivered in stopped state and
   restricted FGS types can't start at boot — `dataSync` type is OK-ish but
   test on Android 15/16

**Verify:** reboot device → notifications still arrive; force-stop app →
behavior matches design.

---

## Step 6 — Deprecation cleanup (refactor, no feature changes)

| Deprecated API | Where | Replace with |
|---|---|---|
| `AsyncTask` (~20 inner classes) | `room/DbRepository.java`, `Settings.java`, `Profile.java`, `RequestFragment.java`, `SchoolViewModel.java` | `Executor` + `Handler(Looper.getMainLooper())` (Room already provides executors) |
| `ProgressDialog` | `Settings`, `HomePage`, `NewsFragment`, `RequestFragment` | Material `AlertDialog` + progress layout |
| `android.preference.PreferenceManager` (16 files) | throughout | `androidx.preference.PreferenceManager` |
| `new Handler()` no looper | `Start.java`, `ChartShow.java` | `new Handler(Looper.getMainLooper())` |
| `NetworkInfo` / `getActiveNetworkInfo` | `Ban.java`, `HomeView.java` | `ConnectivityManager.getNetworkCapabilities` |
| `startActivityForResult` | `Start.java` | existing `ActivityResultLauncher` pattern |
| `getSerializableExtra` untyped | `ChartShow.java` | API 33 typed overload |
| platform `onBackPressed` | `ChartShow.java` | (done properly in Step 3) |

Note: `origin/master` branch ("Asyck Remove") attempted AsyncTask removal on an
**unrelated history** — use only as reference, do not merge.

---

## Step 7 — Dependency updates

- Firebase BOM `30.2.2` → latest stable (`34.x`), google-services `4.4.1` → `4.4.4+`
- Navigation / SafeArgs `2.7.7` → `2.9.x`
- Room `2.6.1` → `2.7.x` (keep `room.schemaLocation` arg)
- AppCompat/Material/RecyclerView — bump to latest
- Ads: `play-services-ads:21.5.0` → latest (24.x) + **UMP consent SDK**
  (AdMob confirmed still active; UMP required for EEA/UK consent)
- Remove `androidx.legacy:legacy-support-v4` if unused after audit
- Remove dead `implementation fileTree(libs/*.jar)` if `libs/` still absent

**Verify:** `gradlew assembleDebug assembleRelease`; smoke test login, charts,
backup, ads.

---

## Step 8 — Dropbox distribution hardening (your release channel)

1. **Integrity check** before install prompt: verify downloaded APK with
   expected **SHA-256** (host checksum in `versionInfo.txt`/`NewadFile.txt`
   alongside the URL) and/or verify signing certificate equals current app cert
2. Keep URLs **HTTPS only** (`SMIConstants.getVersionFile()` etc.)
3. Good error/retry UX in `Start.updateApp()` (network fail ≠ force kill)
4. Optional: second mirror (GitHub Releases) in case Dropbox link dies
5. Revisit kill-switch policy: prefer "update strongly suggested" over
   hard kill so a dead Dropbox link can never brick the app again

---

## Step 9 — (OPTIONAL) Play reinstatement prep

Only if you appeal the ban or open a new developer account:

- Privacy policy (in-app link + Play Console)
- Data safety form (Google sign-in personal data, child health data in
  Room/Firestore, AdMob advertising ID)
- **Account deletion**: in-app path (Settings → re-auth → delete Firestore
  docs + Room rows + `FirebaseAuth.getCurrentUser().delete()`) **and** a public
  web deletion URL
- Ship **AAB** (`gradlew bundleRelease`), bump versionCode each release
- Register package name (Android developer verification)
- Health-app category: confirm whether Organization account is required

---

## Git workflow

- All work happens on branch **`upgrade/android16`**
- Tag per completed milestone: `v4.2.5.5-step1` (done), future tags
  `v4.2.5.5-step2` … as steps complete
- Local only until you decide to push (`git push origin upgrade/android16 --follow-tags`)

## Reference branches (see `branches.md`)

- `origin/2026` — prior Android 16 attempt: reference for Steps 2–3 ideas only
- `origin/master`, `origin/FHB` — abandoned, unrelated history, do not merge
