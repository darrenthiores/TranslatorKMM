import Foundation
import shared

class TranslateRepositoryModule {
    init() {
        @Inject var remoteDataSource: TranslateRemoteDataSource
        @Inject var localDataSource: TranslateLocalDataSource
        
        @Provider var translateRepository: TranslateRepository = TranslateRepositoryImpl(
            remoteDataSource: remoteDataSource,
            localDataSource: localDataSource
        )
    }
}
