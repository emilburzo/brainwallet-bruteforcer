brainwallet-bruteforcer
=======================

Proof-of-concept to demonstrate the security risk in using [brain wallets](http://brainwallet.org/).

## Requirements

* git
* gradle
* java

## How to use

Fire up your favourite terminal and:

```shell
$ git clone https://github.com/emilburzo/brainwallet-bruteforcer.git
$ cd brainwallet-bruteforcer
$ gradle installApp
$ cd build/install/brainwallet-bruteforcer/bin
$ ./brainwallet-bruteforcer <balances file> <passwords file>
```

## Balances file

It should be a CSV file, containing the public key on the first column and the address balance on the second.

If you don't have the balance, that won't affect the main functionality, it's strictly informative.

Sample:

```
1EFJUipfCHFmmTFkF9vvjFKdBf3VbfvarM,183498.40140794
1FfmbHfnpaZjKFvyi1okTjJJusN455paPH,144341.53594935
13Df4x5nQo7boLWHxQCbJzobN5gUNT65Hh,97831.54870760
```

## Password file

One password per line

Sample:

```
1234
1337p455
omghax
```

