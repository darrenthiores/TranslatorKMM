import SwiftUI

@main
struct iOSApp: App {
    
    init() {
        #if DEBUG
        if CommandLine.arguments.contains("isUiTesting") {
            TestAppModule()
            return
        } else {
            AppModule()
            TranslateNetworkModule()
            TranslateDatabaseModule()
            TranslateRepositoryModule()
            TranslateUseCasesModule()
            VoiceToTextDataModule()
        }
        #else
        AppModule()
        TranslateNetworkModule()
        TranslateDatabaseModule()
        TranslateRepositoryModule()
        TranslateUseCasesModule()
        VoiceToTextDataModule()
        #endif
    }
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
