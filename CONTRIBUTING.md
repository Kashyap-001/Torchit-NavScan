# Contributing

Thanks for considering a contribution to Torchit NavScan.

## Setup

1. Fork the repo and clone your fork.
2. Open the project in Android Studio.
3. Requires **minSdk 31** (Android 12) — use a physical device to test, since BLE scanning doesn't work on the emulator.

## Making a change

1. Create a branch off `master`: `git checkout -b fix/short-description`.
2. Make your change. Keep commits focused — one fix or feature per PR.
3. Test on a real device before opening a PR (scan behavior, permission prompts, and TalkBack accessibility should all still work).
4. Push your branch and open a pull request against `master`.

## Pull request description

Explain:
- What was broken or missing, and how you fixed/added it.
- Any manual testing you did (device/Android version used).

Keep PRs small and scoped to one change — easier to review and merge quickly.

## Reporting bugs

Open an issue with steps to reproduce, your Android version, and device model if relevant (BLE behavior can vary by manufacturer).
