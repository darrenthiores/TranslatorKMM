import SwiftUI
import shared
import UniformTypeIdentifiers

struct TranslateTextField: View {
    @Binding var fromText: String
    let toText: String?
    let isTranslating: Bool
    let fromLanguage: UiLanguage
    let toLanguage: UiLanguage
    let onTranslateEvent: (TranslateEvent) -> Void
    
    var body: some View {
        if toText == nil || isTranslating {
            IdleTextField(
                fromText: $fromText,
                isTranslating: isTranslating,
                onTranslateClick: {
                    onTranslateEvent(.Translate())
                }
            )
            .gradientSurface()
            .cornerRadius(15)
            .animation(.easeInOut, value: isTranslating)
            .shadow(radius: 4)
        } else {
            TranslatedTextField(
                fromText: fromText,
                toText: toText ?? "",
                fromLanguage: fromLanguage,
                toLanguage: toLanguage,
                onTranslateEvent: onTranslateEvent
            )
            .padding()
            .gradientSurface()
            .cornerRadius(15)
            .animation(
                .easeInOut,
                value: isTranslating
            )
            .shadow(radius: 4)
            .onTapGesture {
                onTranslateEvent(.EditTranslation())
            }
        }
    }
}

struct TranslateTextField_Previews: PreviewProvider {
    static var previews: some View {
        TranslateTextField(
            fromText: .constant("Hello World!"),
            toText: "Halo",
            isTranslating: false,
            fromLanguage: .companion.byCode(code: "en"),
            toLanguage: .companion.byCode(code: "de"),
            onTranslateEvent: { event in }
        )
    }
}

private extension TranslateTextField {
    struct IdleTextField: View {
        @Binding var fromText: String
        var isTranslating: Bool
        var onTranslateClick: () -> Void
        
        var body: some View {
            TextEditor(
                text: $fromText
            )
            .frame(
                maxWidth: .infinity,
                minHeight: 200,
                alignment: .topLeading
            )
            .padding()
            .foregroundColor(Color.onSurface)
            .overlay(alignment: .bottomTrailing) {
                ProgressButton(
                    text: "Translate",
                    isLoading: isTranslating,
                    onClick: onTranslateClick
                )
                .padding([.trailing, .bottom])
            }
            .onAppear {
                UITextView
                    .appearance()
                    .backgroundColor = .clear
            }
        }
    }
    
    struct TranslatedTextField: View {
        let fromText: String
        let toText: String
        let fromLanguage: UiLanguage
        let toLanguage: UiLanguage
        let onTranslateEvent: (TranslateEvent) -> Void
        
        private let tts = TextToSpeech()
        
        var body: some View {
            VStack(alignment: .leading) {
                LanguageDisplay(language: fromLanguage)
                    .padding(.bottom)
                Text(fromText)
                    .foregroundColor(.onSurface)
                HStack {
                    Spacer()
                    
                    Button {
                        UIPasteboard
                            .general
                            .setValue(
                                fromText,
                                forPasteboardType: UTType.plainText.identifier
                            )
                    } label: {
                        Image(
                            uiImage: UIImage(named: "copy")!
                        )
                        .renderingMode(.template)
                        .foregroundColor(.lightBlue)
                    }
                    
                    Button {
                        onTranslateEvent(.CloseTranslation())
                    } label: {
                        Image(
                            systemName: "xmark"
                        )
                        .foregroundColor(.lightBlue)
                    }
                }
                
                Divider()
                    .padding(.vertical)
                
                LanguageDisplay(language: toLanguage)
                    .padding(.bottom)
                Text(toText)
                    .foregroundColor(.onSurface)
                HStack {
                    Spacer()
                    
                    Button {
                        UIPasteboard
                            .general
                            .setValue(
                                toText,
                                forPasteboardType: UTType.plainText.identifier
                            )
                    } label: {
                        Image(
                            uiImage: UIImage(named: "copy")!
                        )
                        .renderingMode(.template)
                        .foregroundColor(.lightBlue)
                    }
                    
                    Button {
                        tts.speak(
                            text: toText,
                            language: toLanguage.language.langCode
                        )
                    } label: {
                        Image(
                            systemName: "speaker.wave.2"
                        )
                        .foregroundColor(.lightBlue)
                    }
                }

            }
        }
    }
}
