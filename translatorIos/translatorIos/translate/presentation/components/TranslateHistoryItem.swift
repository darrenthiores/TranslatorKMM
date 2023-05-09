import SwiftUI
import shared

struct TranslateHistoryItem: View {
    let item: UiHistory
    let onClick: () -> Void
    
    var body: some View {
        Button(action: onClick) {
            VStack(alignment: .leading) {
                HStack {
                    SmallLanguageIcon(language: item.fromLanguage)
                        .padding(.trailing)
                    Text(item.fromText)
                        .font(.body)
                        .foregroundColor(.lightBlue)
                }
                .padding(.bottom)
                .frame(maxWidth: .infinity, alignment: .leading)
                
                HStack {
                    SmallLanguageIcon(language: item.toLanguage)
                        .padding(.trailing)
                    Text(item.toText)
                        .font(.body.weight(.semibold))
                        .foregroundColor(.onSurface)
                }
                .frame(maxWidth: .infinity, alignment: .leading)
            }
            .frame(maxWidth: .infinity)
            .padding()
            .gradientSurface()
            .cornerRadius(15)
            .shadow(radius: 4)
        }
    }
}

struct TranslateHistoryItem_Previews: PreviewProvider {
    static var previews: some View {
        TranslateHistoryItem(
            item: UiHistory(
                id: 0,
                fromLanguage: .companion.byCode(code: "en"),
                fromText: "Hello World",
                toLanguage: .companion.byCode(code: "de"),
                toText: "Helo Welt"
            ),
            onClick: { }
        )
    }
}
