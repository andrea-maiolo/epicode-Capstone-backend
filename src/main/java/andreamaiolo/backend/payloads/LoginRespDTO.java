package andreamaiolo.backend.payloads;

public record LoginRespDTO(String token,
                           Long userId,
                           String url
) {
}
