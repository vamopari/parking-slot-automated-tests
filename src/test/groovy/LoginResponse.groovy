import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("access_token")
    def access_token;
    @SerializedName("refresh_token")
    def refresh_token;
    @SerializedName("expires_in")
    def expires_in;
    @SerializedName("token_type")
    def token_type;

}
