/**
 * Copyright 2017 Paolo Patierno
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.ù
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.ppatierno.rxjava;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        create();
    }

    private static void create() {

        // 0..N flows, no backpressure
        log.info("Observable");
        Observable.<Integer>create(s -> {

            s.onNext(1);
            s.onNext(2);
            Random random = new Random();
            int r = random.nextInt(10);
            if (r < 5) {
                s.onComplete();
            } else {
                s.onError(new Exception("value = " + r + " -> Error !!!"));
            }

        }).subscribe(
                System.out::println,
                t ->  System.out.println(t.getMessage()),
                () -> System.out.println("Complete !!"));

        // a flow of exactly 1 item or an error
        log.info("Single");
        Single.<Integer>create(s -> {

            Random random = new Random();
            int r = random.nextInt(10);
            if (r < 5) {
                s.onSuccess(r);
            } else {
                s.onError(new Exception("value = " + r + " -> Error !!!"));
            }

        }).subscribe(
                System.out::println,
                t -> System.out.println(t.getMessage()));

        // a flow without items but only a completion or error signal
        log.info("Completable");
        Completable.create(s -> {

            Random random = new Random();
            int r = random.nextInt(10);
            if (r < 5) {
                s.onComplete();
            } else {
                s.onError(new Exception("value = " + r + " -> Error !!!"));
            }

        }).subscribe(
                () -> System.out.println("Complete !!"),
                t -> System.out.println(t.getMessage()));

        // a flow with no items, exactly one item or an error
        log.info("Maybe");
        Maybe.<Integer>create(s -> {

            Random random = new Random();
            int r = random.nextInt(10);
            if (r < 3) {
                s.onComplete();
            } else if (r > 6) {
                s.onSuccess(r);
            } else {
                s.onError(new Exception("value = " + r + " -> Error !!!"));
            }
        }).subscribe(
                System.out::println,
                t -> System.out.print(t.getMessage()),
                () -> System.out.println("Complete !!"));
    }
}
