package com.geektcp.common.openai;

import com.geektcp.common.openai.OpenAiService;
import com.geektcp.common.openai.image.CreateImageEditRequest;
import com.geektcp.common.openai.image.CreateImageRequest;
import com.geektcp.common.openai.image.CreateImageVariationRequest;
import com.geektcp.common.openai.image.Image;


import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;


public class ImageTest {

    static String filePath = "src/test/resources/penguin.png";
    static String fileWithAlphaPath = "src/test/resources/penguin_with_alpha.png";
    static String maskPath = "src/test/resources/mask.png";

    String token = System.getenv("OPENAI_TOKEN");
    OpenAiService service = new OpenAiService(token, 30);


    @Test
    void createImageUrl() {
        CreateImageRequest createImageRequest = CreateImageRequest.builder()
                .prompt("penguin")
                .n(3)
                .size("256x256")
                .user("testing")
                .build();

        List<Image> images = service.createImage(createImageRequest).getData();
        assertEquals(3, images.size());
        assertNotNull(images.get(0).getUrl());
    }

    @Test
    void createImageBase64() {
        CreateImageRequest createImageRequest = CreateImageRequest.builder()
                .prompt("penguin")
                .responseFormat("b64_json")
                .user("testing")
                .build();

        List<Image> images = service.createImage(createImageRequest).getData();
        assertEquals(1, images.size());
        assertNotNull(images.get(0).getB64Json());
    }

    @Test
    void createImageEdit() {
        CreateImageEditRequest createImageRequest = CreateImageEditRequest.builder()
                .prompt("a penguin with a red background")
                .responseFormat("url")
                .size("256x256")
                .user("testing")
                .n(2)
                .build();

        List<Image> images = service.createImageEdit(createImageRequest, fileWithAlphaPath, null).getData();
        assertEquals(2, images.size());
        assertNotNull(images.get(0).getUrl());
    }

    @Test
    void createImageEditWithMask() {
        CreateImageEditRequest createImageRequest = CreateImageEditRequest.builder()
                .prompt("a penguin with a red hat")
                .responseFormat("url")
                .size("256x256")
                .user("testing")
                .n(2)
                .build();

        List<Image> images = service.createImageEdit(createImageRequest, filePath, maskPath).getData();
        assertEquals(2, images.size());
        assertNotNull(images.get(0).getUrl());
    }

    @Test
    void createImageVariation() {
        CreateImageVariationRequest createImageVariationRequest = CreateImageVariationRequest.builder()
                .responseFormat("url")
                .size("256x256")
                .user("testing")
                .n(2)
                .build();

        List<Image> images = service.createImageVariation(createImageVariationRequest, filePath).getData();
        assertEquals(2, images.size());
        assertNotNull(images.get(0).getUrl());
    }
}
