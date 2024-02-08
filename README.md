![GitHub Release](https://img.shields.io/github/v/release/PayamGerackoohi/android-benchmark?style=plastic)
![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/PayamGerackoohi/android-benchmark/main-cicd.yml?style=plastic)
![Coverage](https://img.shields.io/badge/coverage-100%25-brightgreen?style=plastic)
![GitHub top language](https://img.shields.io/github/languages/top/PayamGerackoohi/android-benchmark?style=plastic)
![GitHub last commit](https://img.shields.io/github/last-commit/PayamGerackoohi/android-benchmark?style=plastic)
![GitHub License](https://img.shields.io/github/license/PayamGerackoohi/android-benchmark?style=plastic)

![GitHub repo file or directory count](https://img.shields.io/github/directory-file-count/PayamGerackoohi/android-benchmark?style=plastic)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/PayamGerackoohi/android-benchmark?style=plastic)
![GitHub repo size](https://img.shields.io/github/repo-size/PayamGerackoohi/android-benchmark?style=plastic)

<!-- ![GitHub tag checks state](https://img.shields.io/github/checks-status/PayamGerackoohi/android-benchmark/0.0.1?style=plastic)
![GitHub Downloads (all assets, all releases)](https://img.shields.io/github/downloads/PayamGerackoohi/android-benchmark/total?style=plastic)
![GitHub Downloads (all assets, latest release)](https://img.shields.io/github/downloads/PayamGerackoohi/android-benchmark/latest/total?style=plastic)
![GitHub Sponsors](https://img.shields.io/github/sponsors/PayamGerackoohi?style=plastic)
![GitHub Repo stars](https://img.shields.io/github/stars/PayamGerackoohi/android-benchmark?style=plastic)
![GitHub User's stars](https://img.shields.io/github/stars/PayamGerackoohi?style=plastic)
![AppVeyor tests](https://img.shields.io/appveyor/tests/PayamGerackoohi/android-benchmark?style=plastic) -->

# Android Benchmark
A benchmark to integrate the latest technologies in android realm altogether.

## Technologies
- [x] Compose (BOM 2023.03.00)
- [x] Kotlin (v1.8.10)
- [x] Hilt (v2.44)
- [x] Mavericks (v3.0.7)
- [x] Unit Test (Junit v5.10.0)
- [x] UI Test (Junit v4.13.2)
- [x] Kover (v0.7.4)
- [x] Jacoco (v0.8.11)
- [x] Google Splashscreen API (v1.0.1)

# Screenshots
The screenshots are automatically captured, resized and compressed from the instrumented testes.

## Splashscreen
![splashscreen](docs/screenshots/Splashscreen.webp)

## Calculator
Basic integer calculator, extended to the complex numbers realm.
 
![calculator](docs/screenshots/Calculator.webp)

# Code Quality
## Test Results ✅
### Unit Tests ![Unit Tests](https://img.shields.io/badge/passed-100%25-brightgreen?style=plastic)
![word detail](docs/test-results/unit_tests.webp)

### UI Tests ![UI Tests](https://img.shields.io/badge/passed-100%25-brightgreen?style=plastic)
![word detail](docs/test-results/ui_tests.webp)

## Test Coverage 👍
### Kover Report ![Kover Report](https://img.shields.io/badge/coverage-100%25-brightgreen?style=plastic)
![word detail](docs/test-results/kover.webp)

### Jacoco Report ![Jacoco Report](https://img.shields.io/badge/coverage-96%25-green?style=plastic)
![word detail](docs/test-results/jacoco.webp)

# Scripts
Some Unix scripts for the CI/CD and to make the screenshot capturing automatic.
Run the scripts from the project root directory.

## `take-screenshots.sh`
<!-- - Edit `adb` and `JAVA_HOME` in the script to the  -->
For the first time, set your `adb` and `JAVA_HOME` for `jdk-17` into the `env` file. Similar this on OSX:
```sh
rm -f scripts/env
echo 'adb=/Users/payam1991gr/Library/Android/sdk/platform-tools/adb' >> scripts/env
echo 'JAVA_HOME=/Applications/Android Studio.app/Contents/jbr/Contents/Home' >> scripts/env
```

From now on
- Run an android emulator device
- Run the script
```sh
./scripts/take-screenshots.sh
```
The results are stored in the `./docs/screenshots` directory.

❌ Don't put anything inside the `./docs/screenshots` folder. It would be cleaned-up everytime you call the `take-screenshots.sh` script.

## Todo
- [x] Github Actions
  - [x] Main Branch Release Build and Tag
  - [x] Report Badges
- [ ] Networking (GraphQL/Retrofit)
- [ ] Room
- [ ] Compose Animations
- [ ] Compose Gestures
- [ ] Compose Accessibility
- [ ] Native C++
