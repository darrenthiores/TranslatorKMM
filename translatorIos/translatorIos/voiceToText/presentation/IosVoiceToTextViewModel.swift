import Foundation
import shared
import Combine

@MainActor class IosVoiceToTextViewModel: ObservableObject {
    private let languageCode: String
    private let viewModel: VoiceToTextViewModel
    @Published var state = VoiceToTextState(
        powerRatios: [],
        spokenText: "",
        canRecord: false,
        recordError: nil,
        displayState: nil
    )
    private var handle: DisposableHandle?
    
    init(languageCode: String) {
        @Inject var parser: VoiceToTextParser
        
        self.languageCode = languageCode
        self.viewModel = VoiceToTextViewModel(
            parser: parser,
            coroutineScope: nil
        )
        
        self.viewModel.onEvent(
            event: .PermissionResult(
                isGranted: true,
                isPermanentlyDeclined: false
            )
        )
    }
    
    func onEvent(event: VoiceToTextEvent) {
        viewModel.onEvent(event: event)
    }
    
    func startObserving() {
        handle = viewModel.state.subscribe { [weak self] state in
            if let state {
                self?.state = state
            }
        }
    }
    
    func dispose() {
        handle?.dispose()
        onEvent(event: .Reset())
    }
}
