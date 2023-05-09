import SwiftUI
import shared

struct TranslateScreen: View {
    @StateObject private var viewModel = IosTranslateViewModel()
    
    var body: some View {
        NavigationView {
            ZStack {
                Color.background
                    .ignoresSafeArea()
                
                List {
                    HStack(alignment: .center) {
                        LanguageDropDown(
                            language: viewModel.state.fromLanguage,
                            isOpen: viewModel.state.isChoosingFromLanguage,
                            selectLanguage: { language in
                                viewModel.onEvent(event: .ChooseFromLanguage(language: language))
                            }
                        )
                        
                        Spacer()
                        
                        SwapLanguageButton(
                            onClick: {
                                viewModel.onEvent(event: .SwapLanguages())
                            }
                        )
                        
                        Spacer()
                        
                        LanguageDropDown(
                            language: viewModel.state.toLanguage,
                            isOpen: viewModel.state.isChoosingToLanguage,
                            selectLanguage: { language in
                                viewModel.onEvent(event: .ChooseToLanguage(language: language))
                            }
                        )
                    }
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color.background)
                    
                    TranslateTextField(
                        fromText: Binding(
                            get: {
                                viewModel.state.fromText
                            },
                            set: {
                                viewModel.onEvent(
                                    event: .ChangeTranslationText(text: $0)
                                )
                            }
                        ),
                        toText: viewModel.state.toText,
                        isTranslating: viewModel.state.isTranslating,
                        fromLanguage: viewModel.state.fromLanguage,
                        toLanguage: viewModel.state.toLanguage,
                        onTranslateEvent: { event in
                            viewModel.onEvent(event: event)
                        }
                    )
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color.background)
                    
                    if !viewModel.state.history.isEmpty {
                        Text("History")
                            .font(.title)
                            .bold()
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .listRowSeparator(.hidden)
                            .listRowBackground(Color.background)
                    }
                    
                    ForEach(
                        viewModel.state.history,
                        id: \.self.id
                    ) { history in
                        TranslateHistoryItem(
                            item: history,
                            onClick: {
                                viewModel.onEvent(
                                    event: .SelectHistory(item: history)
                                )
                            }
                        )
                        .listRowSeparator(.hidden)
                        .listRowBackground(Color.background)
                    }
                }
                .listStyle(.plain)
                .buttonStyle(.plain)
                
                VStack {
                    Spacer()
                    
                    NavigationLink {
                        VoiceToTextScreen(
                            languageCode: viewModel.state.fromLanguage.language.langCode,
                            onResult: { spokenText in
                                viewModel.onEvent(
                                    event: .SubmitVoiceResult(result: spokenText)
                                )
                            }
                        )
                    } label: {
                        ZStack {
                            Circle()
                                .foregroundColor(.primaryColor)
                                .padding()
                                .frame(width: 100, height: 100)
                            
                            Image(
                                uiImage: UIImage(named: "mic")!
                            )
                            .foregroundColor(.onPrimary)
                            .accessibilityIdentifier("record audio")
                        }
                        .frame(width: 100, height: 100)
                    }
                }
            }
            .onAppear {
                viewModel.startObserving()
            }
            .onDisappear {
                viewModel.dispose()
            }
        }
    }
}

struct TranslateScreen_Previews: PreviewProvider {
    static var previews: some View {
        TranslateScreen()
    }
}
