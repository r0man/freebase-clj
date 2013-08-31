# freebase-clj [![Build Status](https://travis-ci.org/r0man/freebase-clj.png)](https://travis-ci.org/r0man/freebase-clj)

Clojure Library for Freebase.

## Installation

Via Clojars: http://clojars.org/freebase-clj

## Usage

Find an object in the database whose type is "/music/artist" and whose name is "The Police". Then return its set of albums.

    (require 'freebase.core)

    (query {:type :/music/artist :name "The Police" :album []})
    ;=> {:album
    ;=>  ["Outlandos d'Amour"
    ;=>   "Reggatta de Blanc"
    ;=>   "Zenyattà Mondatta"
    ;=>   "Ghost in the Machine"
    ;=>   "Synchronicity"
    ;=>   "Every Breath You Take: The Singles"
    ;=>   "Message in a Box: The Complete Recordings"
    ;=>   "Live!"
    ;=>   "Every Breath You Take: The Classics"
    ;=>   "Their Greatest Hits"
    ;=>   "Roxanne '97"
    ;=>   "The Police"
    ;=>   "Greatest Hits"
    ;=>   "The Very Best of Sting &amp; The Police"
    ;=>   "Brimstone &amp; Treacle"
    ;=>   "De Do Do Do, De Da Da Da"
    ;=>   "Certifiable: Live in Buenos Aires"
    ;=>   "Roxanne"
    ;=>   "2007-09-16: Geneva"
    ;=>   "Live in Boston"
    ;=>   "The 50 Greatest Songs"
    ;=>   "King of Pain"
    ;=>   "Invisible Sun"
    ;=>   "Message in a Bottle"
    ;=>   "Spirits in the Material World"
    ;=>   "Don't Stand So Close to Me '86"
    ;=>   "When the World Is Running Down (You Can't Go Wrong)"
    ;=>   "Reunion Concert 1986"
    ;=>   "1981-02-02: Budokan Hall, Tokyo, Japan"
    ;=>   "Every Breath You Take"
    ;=>   "Can't Stand Losing You"
    ;=>   "Six Pack"
    ;=>   "Fall Out"
    ;=>   "So Lonely"
    ;=>   "Bring on the Night"
    ;=>   "Walking on the Moon"
    ;=>   "The Bed's Too Big Without You"
    ;=>   "Don't Stand So Close to Me"
    ;=>   "Every Little Thing She Does Is Magic"
    ;=>   "Secret Journey"
    ;=>   "Wrapped Around Your Finger"
    ;=>   "Synchronicity II"
    ;=>   "When the World Is Running Down, You Make the Best of What's Still Around"
    ;=>   "Voices in my Head"
    ;=>   "Backing Track"
    ;=>   "Can't Stand Losing You"],
    ;=>  :name "The Police",
    ;=>  :type "/music/artist"}

## License

Copyright (C) 2013 Roman Scherer

Distributed under the Eclipse Public License, the same as Clojure.
