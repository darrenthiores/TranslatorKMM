import SwiftUI
import shared

struct VoiceRecorderButton: View {
    let displayState: DisplayState
    let onClick: () -> Void
    
    var body: some View {
        Button(action: onClick) {
            ZStack {
                Circle()
                    .foregroundColor(.primaryColor)
                    .padding()
                
                icon
                    .foregroundColor(.onPrimary)
            }
        }
        .frame(maxWidth: 100.0, maxHeight: 100.0)
        .accessibilityIdentifier("voice recorder button")
    }
    
    var icon: some View {
        switch displayState {
        case .speaking:
            return Image(systemName: "stop.fill")
        case .displayingresult:
            return Image(systemName: "checkmark")
        default:
            return Image(uiImage: UIImage(named: "mic")!)
        }
    }
}

struct VoiceRecorderButton_Previews: PreviewProvider {
    static var previews: some View {
        VoiceRecorderButton(
            displayState: .waitingtotalk,
            onClick: {}
        )
    }
}
