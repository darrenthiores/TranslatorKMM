import SwiftUI
import shared

struct ProgressButton: View {
    let text: String
    let isLoading: Bool
    let onClick: () -> Void
    
    var body: some View {
        Button {
            if !isLoading {
                onClick()
            }
        } label: {
            if isLoading {
                ProgressView()
                    .animation(
                        .easeInOut,
                        value: isLoading
                    )
                    .padding(5)
                    .background(
                        Circle()
                            .foregroundColor(Color.primaryColor)
                    )
                    .progressViewStyle(
                        CircularProgressViewStyle(tint: .white)
                    )
            } else {
                Text(text.uppercased())
                    .animation(
                        .easeInOut,
                        value: isLoading
                    )
                    .padding(.horizontal)
                    .padding(.vertical, 5)
                    .font(.body.weight(.bold))
                    .background(Color.primaryColor)
                    .foregroundColor(Color.onPrimary)
                    .cornerRadius(100)
            }
        }
    }
}

struct ProgressButton_Previews: PreviewProvider {
    static var previews: some View {
        ProgressButton(
            text: "Translate",
            isLoading: true,
            onClick: {}
        )
        
        ProgressButton(
            text: "Translate",
            isLoading: false,
            onClick: {}
        )
    }
}
