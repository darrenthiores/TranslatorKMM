import SwiftUI
import shared

struct LanguageDropDown: View {
    let language: UiLanguage
    let isOpen: Bool
    let selectLanguage: (UiLanguage) -> Void
    
    var body: some View {
        Menu {
            VStack {
                ForEach(
                    UiLanguage.companion.allLanguages,
                    id: \.self.language.langCode
                ) { language in
                    LanguageDropDownItem(
                        language: language,
                        onClick: {
                            selectLanguage(language)
                        }
                    )
                }
            }
        } label: {
            HStack {
                SmallLanguageIcon(language: language)
                
                Text(language.language.langName)
                    .foregroundColor(.lightBlue)
                
                Image(systemName: isOpen ? "chevron.up" : "chevron.down")
                    .foregroundColor(.lightBlue)
            }
        }
    }
}

struct LanguageDropDown_Previews: PreviewProvider {
    static var previews: some View {
        LanguageDropDown(
            language: .companion.byCode(code: "en"),
            isOpen: true,
            selectLanguage: { language in }
        )
    }
}
