package com.reactive.init;

import com.reactive.model.Kayak;
import com.reactive.repository.KayakRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

@Component
class Initializer implements CommandLineRunner {
    private final KayakRepository kayakRepository;

    public Initializer(KayakRepository kayakRepository) {
        this.kayakRepository = kayakRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Object[][] data = {
                { "sea", "Andrew", 300.12, "NDK" },
                { "creek", "Andrew", 100.75, "Piranha" },
                { "loaner", "Andrew", 75, "Necky" }
        };

        kayakRepository
                .deleteAll()
                .thenMany(
                        Flux
                                .just(data)
                                .map(array -> {
                                    return new Kayak((String) array[0], (String) array[1], (Number) array[2],
                                            (String) array[3]);
                                })
                                .flatMap(kayakRepository::save))
                .thenMany(kayakRepository.findAll())
                .subscribe(kayak -> System.out.println("saving " + kayak.toString()));
    }
}
