import Foundation
import shared

class TranslateDatabaseModule {
    init() {
        @Provider var driver = DatabaseDriverFactory().create()
        @Provider var translateDatabase = DatabaseFactory().createDatabase(driver: driver)
        @Provider var historyDao: HistoryDao = SqlDelightHistoryDao(db: translateDatabase)
        @Provider var translateLocalDataSource: TranslateLocalDataSource = TranslateLocalDataSource(
            historyDao: historyDao
        )
    }
}
