package com.example.github.apiAvailabilityTest

import org.junit.Test
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import java.net.URL
import java.nio.charset.Charset

class APIAvailabilityTest {
    @Test
    @Throws(Exception::class)
    fun testAvailability() {
        val connection =
            URL("https://api.github.com/search/repositories?q=tetris&sort=stars&order=desc").openConnection()
        val response = connection.getInputStream()

        val buffer = StringBuffer()
        BufferedReader(
            InputStreamReader(
                response,
                Charset.defaultCharset()
            ) as Reader
        ).use { reader ->
            var line: String?
            do {
                line = reader.readLine()
                if (line == null)
                    break
                buffer.append(line)

            } while (true)
        }

        assert(buffer.isNotEmpty())
    }
}