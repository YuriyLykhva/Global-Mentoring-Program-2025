package core.api.api_client;

public interface IApiClient {

    CustomResponse post(String url, Object requestBody);

    CustomResponse put(String url, Object requestBody);

    CustomResponse get(String url);

    CustomResponse delete(String url);
}
