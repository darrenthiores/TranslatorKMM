import Foundation
import shared

class VoiceToTextDataModule {
    init() {
        @Provider var voiceToTextParser: VoiceToTextParser = IosVoiceToTextParser()
    }
}
