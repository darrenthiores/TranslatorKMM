import SwiftUI
import shared

struct LanguageDisplay: View {
    let language: UiLanguage
    
    var body: some View {
        HStack {
            SmallLanguageIcon(language: language)
                .padding(.trailing, 4)
            
            Text(language.language.langName)
                .foregroundColor(.lightBlue)
        }
    }
}

struct LanguageDisplay_Previews: PreviewProvider {
    static var previews: some View {
        LanguageDisplay(
            language: .companion.byCode(code: "en")
        )
    }
}
