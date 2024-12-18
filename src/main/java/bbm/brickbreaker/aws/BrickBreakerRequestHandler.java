package bbm.brickbreaker.aws;

import basicneuralnetwork.NeuralNetwork;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.IOException;
import java.io.InputStream;

public class BrickBreakerRequestHandler implements RequestHandler<BrickBreakerRequestHandler.BrickBreakerRequest, BrickBreakerRequestHandler.BrickBreakerResponse> {

    private final S3Client s3Client;

    public BrickBreakerRequestHandler() {
        s3Client = S3Client.create();
    }

    @Override
    public BrickBreakerResponse handleRequest(BrickBreakerRequest request, Context context) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket("bbm.bbnn")
                    .key("ai.json")
                    .build();

            InputStream in = s3Client.getObject(getObjectRequest);
            NeuralNetwork network = NeuralNetwork.readFromFile(in);

            double guess[] = new double[4];
            guess[0] = request.xBall(); // x ball
            guess[1] = request.xPaddle(); // x paddle
            guess[2] = request.xBrick(); // x brick
            guess[3] = request.yBrick(); // y brick
            double[] output = network.guess(guess);

            BrickBreakerResponse response = new BrickBreakerResponse(
                    output[0], output[1]
            );
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    record BrickBreakerRequest
            (
                    double xBall,
                    double xPaddle,
                    double xBrick,
                    double yBrick
            ) {

    }

    record BrickBreakerResponse
            (
                    // want to see what the difference in these values are.
                    double right,
                    double left
            ) {
        public String moveString() {
            if (right > left) {
                return "RIGHT";
            } else {
                return "LEFT";
            }
        }
    }
}