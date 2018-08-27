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
$ gradle installDist
$ cd build/install/brainwallet-bruteforcer/bin
$ ./brainwallet-bruteforcer <balances file> <passwords file>
```

## Balances file

One bitcoin public key per line

Sample:

```
1EFJUipfCHFmmTFkF9vvjFKdBf3VbfvarM
1FfmbHfnpaZjKFvyi1okTjJJusN455paPH
13Df4x5nQo7boLWHxQCbJzobN5gUNT65Hh
```

## Password file

One password per line

Sample:

```
1234
1337p455
omghax
```

