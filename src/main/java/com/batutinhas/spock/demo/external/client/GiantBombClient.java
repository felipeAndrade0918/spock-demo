package com.batutinhas.spock.demo.external.client;

import com.batutinhas.spock.demo.external.dto.SearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "giantBomApi", url = "${api.host}")
public interface GiantBombClient {

    @GetMapping(value = "/search?api_key=${api.key}&format=json&resources=game",
            headers = {"User-Agent=SpockDemoApi"})
    SearchResponse searchGames(@RequestParam String query);
}
