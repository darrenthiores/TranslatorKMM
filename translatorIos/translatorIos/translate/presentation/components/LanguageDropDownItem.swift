import SwiftUI
import shared

struct LanguageDropDownItem: View {
    let language: UiLanguage
    let onClick: () -> Void
    
    var body: some View {
        Button(action: onClick) {
            HStack {
                if let image = UIImage(
                    named: language
                        .imageName
                        .lowercased()
                ) {
                    Image(uiImage: image)
                        .resizable()
                        .frame(width: 40, height: 40)
                        .padding(.trailing, 4)
                    
                    Text(language.language.langName)
                        .foregroundColor(.textBlack)
                }
            }
        }
    }
}

struct LanguageDropDownItem_Previews: PreviewProvider {
    static var previews: some View {
        LanguageDropDownItem(
            language: .companion.byCode(code: "en"),
            onClick: { }
        )
    }
}
