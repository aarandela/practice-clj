# practice

Practice on doing some backend Clojure. Simple skeleton of what a backend might look like.
Used `lein new app practice` to get a template set up
I first started working on taking in different files to display. This is in `file_contents.clj` and `util.clj`.
Then I wrote some tests to verify the logic that was being done.
Getting a simple server started was the next step in practicing this backend clojure, then writing a small REST API.

Some TODO items:
- more/better validation
- more meaningful tests or testing the api


## Usage

To use the CLI `lein run filename sort-mode`

```sh
# open pipes.txt file and sorted by email (descending). Then by last name ascending.
lein run ./records/pipes.txt 1

# open commas.csv file and sorted by birth date, ascending.
lein run ./records/commas.csv 2

# open spaces.txt file and sorted by last name, descending.
lein run ./records/spaces.txt 3
```


Use `lein run server` to start the server.

```sh
# Test the API
## returns records sorted by email
curl http:/127.0.0.1:3000/records/email
## To check for values in a certain file (commas.csv, spaces.txt, pipes.txt)
curl http:/127.0.0.1:3000/records/email\?file\=./records/commas.csv

## returns records sorted by birthdate
curl http:/127.0.0.1:3000/records/birthdate
## To check for values in a certain file (commas.csv, spaces.txt, pipes.txt)
curl http:/127.0.0.1:3000/records/birthdate\?file\=./records/spaces.txt

## returns records sorted by last name
curl http:/127.0.0.1:3000/records/name
## To check for values in a certain file (commas.csv, spaces.txt, pipes.txt)
curl http:/127.0.0.1:3000/records/name\?file\=./records/pipes.txt

## POST a new line in the spaces.txt file
curl -d 'new-line=lastName firstName email favoriteColor 2/2/2000' http:/127.0.0.1:3000/records
## POST a new line in the commas.csv file
curl -d 'new-line=lastName firstName, email, favoriteColor, 2/2/2000' http:/127.0.0.1:3000/records
## POST a new line in the pipes.txt file
curl -d 'new-line=lastName firstName | email | favoriteColor | 2/2/2000' http:/127.0.0.1:3000/records
```
