import Foundation
import shared

class TranslateNetworkModule {
    init() {
        @Inject var dispatchers: DispatchersProvider
        
        @Provider var client = HttpClientFactory().create()
        @Provider var translateApi: TranslateApi = KtorTranslateApi(client: client)
        @Provider var translateRemoteDataSource: TranslateRemoteDataSource = TranslateRemoteDataSource(
            translateApi: translateApi,
            dispatchers: dispatchers
        )
    }
}
