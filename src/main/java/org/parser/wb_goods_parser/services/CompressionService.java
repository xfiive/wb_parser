package org.parser.wb_goods_parser.services;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class CompressionService {
    public static String compress(@NotNull String data) throws Exception {
        byte[] input = data.getBytes(StandardCharsets.UTF_8);
        Deflater deflater = new Deflater();
        deflater.setInput(input);
        deflater.finish();
        byte[] buffer = new byte[1024];
        int compressedDataLength = deflater.deflate(buffer);
        byte[] output = new byte[compressedDataLength];
        System.arraycopy(buffer, 0, output, 0, compressedDataLength);
        return Base64.getEncoder().encodeToString(output);
    }

    @Contract("_ -> new")
    public static @NotNull String decompress(String compressedData) throws Exception {
        byte[] input = Base64.getDecoder().decode(compressedData);
        Inflater inflater = new Inflater();
        inflater.setInput(input);
        byte[] buffer = new byte[1024];
        int resultLength = inflater.inflate(buffer);
        inflater.end();
        return new String(buffer, 0, resultLength, StandardCharsets.UTF_8);
    }

}
