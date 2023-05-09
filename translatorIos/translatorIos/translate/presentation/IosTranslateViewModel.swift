import Foundation
import shared

extension TranslateScreen {
    @MainActor class IosTranslateViewModel: ObservableObject {
        private let viewModel: TranslateViewModel
        
        @Published var state: TranslateState = TranslateState(
            fromText: "",
            toText: nil,
            isTranslating: false,
            fromLanguage: .companion.byCode(code: "en"),
            toLanguage: .companion.byCode(code: "de"),
            isChoosingFromLanguage: false,
            isChoosingToLanguage: false,
            error: nil,
            history: []
        )
        
        private var handle: DisposableHandle?
        
        init() {
            @Inject var translateUseCase: Translate
            @Inject var getHistoryUseCase: GetHistory
            
            self.viewModel = TranslateViewModel(
                translate: translateUseCase,
                getHistory: getHistoryUseCase,
                coroutineScope: nil
            )
        }
        
        func onEvent(event: TranslateEvent) {
            viewModel.onEvent(event: event)
        }
        
        func startObserving() {
            handle = viewModel.state.subscribe { state in
                if let state = state {
                    self.state = state
                }
            }
        }
        
        func dispose() {
            handle?.dispose()
        }
    }
}
