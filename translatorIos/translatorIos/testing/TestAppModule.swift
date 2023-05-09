import Foundation
import shared

class TestAppModule {
    init() {
        @Provider var fakeRepository: TranslateRepository = FakeTranslateRepository()
        @Provider var translateUseCase: Translate = Translate(repository: fakeRepository)
        @Provider var getHistoryUseCase: GetHistory = GetHistory(repository: fakeRepository)
        @Provider var voiceToTextParser: VoiceToTextParser = FakeVoiceToTextParser()
    }
}
