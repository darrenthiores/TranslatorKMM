import Foundation
import shared

class TranslateUseCasesModule {
    init() {
        @Inject var translateRepository: TranslateRepository
        
        @Provider var translateUseCase: Translate = Translate(repository: translateRepository)
        @Provider var getHistoryUseCase: GetHistory = GetHistory(repository: translateRepository)
    }
}
