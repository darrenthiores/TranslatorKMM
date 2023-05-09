import Foundation
import shared

class AppModule {
    init() {
        @Provider var dispatchers: DispatchersProvider = StandardDispatchers()
    }
}
