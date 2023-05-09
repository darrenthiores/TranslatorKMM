import SwiftUI
import shared

struct VoiceToTextScreen: View {
    private let languageCode: String
    private let onResult: (String) -> Void
    @StateObject var viewModel: IosVoiceToTextViewModel
    @Environment(\.presentationMode) var presentation
    
    init(
        languageCode: String,
        onResult: @escaping (String) -> Void
    ) {
        self.languageCode = languageCode
        self.onResult = onResult
        _viewModel = StateObject(
            wrappedValue: IosVoiceToTextViewModel(
                languageCode: languageCode
            )
        )
    }
    
    var body: some View {
        VStack {
            Spacer()
            
            mainView
            
            Spacer()
            
            HStack {
                Spacer()
                
                VoiceRecorderButton(
                    displayState: viewModel.state.displayState ?? .waitingtotalk,
                    onClick: {
                        if viewModel.state.displayState != .displayingresult {
                            viewModel.onEvent(event: .ToggleRecording(languageCode: languageCode))
                        } else {
                            onResult(viewModel.state.spokenText)
                            self.presentation.wrappedValue.dismiss()
                        }
                    }
                )
                
                if viewModel.state.displayState == .displayingresult {
                    Button {
                        viewModel.onEvent(event: .ToggleRecording(languageCode: languageCode))
                    } label: {
                        Image(systemName: "arrow.clockwise")
                            .foregroundColor(.lightBlue)
                    }
                }
                
                Spacer()
            }
        }
        .onAppear {
            viewModel.startObserving()
        }
        .onDisappear {
            viewModel.dispose()
        }
        .background(.background)
    }
    
    var mainView: some View {
        if let displayState = viewModel.state.displayState {
            switch displayState {
            case .waitingtotalk:
                return AnyView(
                    Text("Click record and start talking")
                        .font(.title2)
                )
            case .displayingresult:
                return AnyView(
                    Text(viewModel.state.spokenText)
                        .font(.title2)
                )
            case .error:
                return AnyView(
                    Text(viewModel.state.recordError ?? "Unknown Error")
                        .font(.title2)
                        .foregroundColor(.red)
                )
            case .speaking:
                return AnyView(
                    VoiceRecorderDisplay(
                        powerRatios: viewModel.state.powerRatios.map { ratio in
                            Double(truncating: ratio)
                        }
                    )
                    .frame(maxHeight: 100)
                    .padding()
                )
            default:
                return AnyView(
                    EmptyView()
                )
            }
        } else {
            return AnyView(
                EmptyView()
            )
        }
    }
}

struct VoiceToTextScreen_Previews: PreviewProvider {
    static var previews: some View {
        VoiceToTextScreen(
            languageCode: "en",
            onResult: { result in }
        )
    }
}
