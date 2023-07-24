# TranslatorKMM

This app is a translator app, works just like any translation app, user can choose which language to which language and just type the word or sentence they want to translate. Any translated words will be inserted to local database as a history which user can easily use at any time. Not just by text, user can use voice to text translation. This app use KMM (Kotlin Multiplatform Mobile) for the data layer to view model and supports Android and IOS.

# Platforms

The app used different Voice to Text Parser:

For Android SpeechRecognizer, and for IOS SFSpeechRecognizer

# Running The App

On IOS, to enable local database using SqlDelight:
1. Go to translatorIos Target and Build Settings
2. Search for Other Linker Flags
3. add "-lsqlite3"

Only on IOS if you use an arm64 architecture macbook, then you need to exclude arm64 in Pods to makes it work, the step:
1. Go to Pods Project and Build Settings
2. Find Excluded Architectures and add "arm64"

# Demo

![Portfolio - Darren (1)](https://github.com/darrenthiores/TranslatorKMM/assets/69592810/a04b021c-6207-49ae-8d99-e460988108b7)

# Technologies and Libraries

- KMM (Kotlin Multiplatform Mobile)
- Kotlin
- Swift
- Jetpack Compose
- SwiftUI
- Ktor (Api Service)
- SqlDelight (Local Database)
