# Branch audit — SMI4.2.5 (generated 2026-11-06)

All branches known to this clone. "Ahead/behind" is relative to `main` (e23f9e9).

## Summary table

| Branch | Tip | Ahead / Behind | Common history with main? | Verdict |
|---|---|---|---|---|
| **`main`** (= `origin/main`, HEAD) | `e23f9e9` Merge remote-tracking branch 'origin/main' | — | — | ✅ base — keep |
| **`upgrade/android16`** (new, local) | created 2026-11-06 from `main` | 0+ | yes | ✅ **active work branch** — all Step 1–9 changes go here |
| **`origin/2026`** — "android 16 supprt 2026" | `ca3e6e2` | ahead 3 / behind 2 | yes | ⚠️ **reference only — do not merge** (details below) |
| **`origin/master`** — "agp Updated" | `3a10ce3` | ahead 2 / behind 16 | ❌ **no merge base** | ❌ **abandoned** — superseded (details below) |
| **`origin/FHB`** | `c23f8a5` | ahead 3 / behind 16 | ❌ **no merge base** | ❌ **useless/abandoned** — delete candidate |

---

## `origin/2026` — prior Android 16 attempt ⚠️ reference only

**Commits (3, ahead of main):**
```
ca3e6e2 android 16 supprt 2026
c63aaca polish Old code 2026/01/15
6ea6991 polish Old code 2026/01/15
```

**What it already did:**
- `compileSdk 36`, **`targetSdk 36`**, `minSdk 23` (we kept targetSdk 34 until Step 3)
- AGP **8.2.2** + Gradle **8.6** (we now have newer: AGP 8.9.2 + Gradle 8.11.1)
- google-services 4.4.4, `buildFeatures.buildConfig true` (same fix we applied)
- versionCode `4265` / versionName `4.2.6.5`
- `Start.java`: kill-switch changed `2024` → **2030** (we will *remove* it in Step 2 — removal is better than postponing)
- `service/Notify.java`: −55 lines cleanup (worth reviewing for our Step 2/5)
- `SchoolViewModel.java`, `HomeView.java`, `strings.xml`: small cleanups

**Why not merge:**
1. Its toolchain (AGP 8.2.2 / Gradle 8.6) is **older** than what `main` now
   builds with after Step 1 — merging would regress the build config
2. Commits are **massively polluted**: ~3022 files changed, including
   `.gradle/` caches, `MPChartLib/build/` outputs, `Thumbs.db` — never meant to merge
3. targetSdk 36 behavioral work (edge-to-edge / predictive back) is **not**
   visible in its diff — it only bumped the number, so the hard part is undone

**How to use it:** as a ideas/checklist source for Steps 2–5
(`git diff main...origin/2026 -- app/src`), cherry-pick nothing blindly.

---

## `origin/master` — old AGP + AsyncTask attempt ❌ abandoned

**Commits (2, ahead):**
```
3a10ce3 2025/01/24 agp Updated
89a6103 2025/01/19 Asyck Remove
```

**What it contains:**
- AGP `8.5.2` attempt (with old `rootProject.buildDir` style — i.e. half-migrated)
- **"Asyck Remove"**: AsyncTask removed/refactored across ~30 files
  (`DbRepository`, `SMIData`, `SmiDataEntry`, `General`, `RequestFragment`,
  `Profile`, …) plus `AndroidManifest` and chart-screen changes
- New `StudentDao` methods, `GeneralViewModel` changes

**Why not merge:**
1. **No merge base with `main`** — unrelated root history; merging is a
   content-level rewrite conflict across 30+ files, not a normal merge
2. Superseded: our Step 1 toolchain is newer; our planned Step 6 covers
   AsyncTask removal on the *current* codebase
3. Untested — no evidence it was ever released

**How to use it:** optional reference when doing Step 6
(`git show origin/master:app/src/main/java/sss/yml/smi/room/DbRepository.java`).

---

## `origin/FHB` — 2024 branch ❌ useless

**Commits (3, ahead):**
```
1ce65dc 2024/03/31
ce55fb9 2024/03/31
bd1d3ec 2024-03-30
```

- **No merge base with `main`** — unrelated history (repo root rewritten at some point)
- 2+ years stale, behind main by everything, contents not worth adopting
- Name likely an old feature/abbreviation ("FHB")

**Recommendation:** safe to ignore; optionally delete from remote later
(`git push origin --delete FHB`) after you confirm nobody needs it.

---

## Housekeeping recommendations

1. `origin/2026` — keep as reference until Steps 2–5 are done, then optionally delete
2. `origin/master`, `origin/FHB` — delete candidates once you're confident
3. The repo has **no other local branches**; work stays on `upgrade/android16`
4. `.gitignore` should eventually cover `.gradle/`, `build/`, `Thumbs.db`,
   `local.properties` (the `2026` branch pollution shows they were committed before)
