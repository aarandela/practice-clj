# practice

Practice on doing some backend Clojure.

Used `lein new app practice` to get a template set up

I first started working on taking in different files to display. This is in `file_contents.clj` and `util.clj`.

Then I wrote some tests to verify the logic that was being done.

Getting a simple server started was the next step in practicing this backend clojure, then writing a small REST API.

## Usage

Use `lein run` to start the server.

```sh
# Test the API
## returns records sorted by email
curl http:/127.0.0.1:3000/records/email
## To check for values in a certain file (pipes.txt, commas.csv, spaces.txt)
curl http:/127.0.0.1:3000/records/email\?file\=commas.csv

## returns records sorted by birthdate
curl http:/127.0.0.1:3000/records/birthdate
## To check for values in a certain file (pipes.txt, commas.csv, spaces.txt)
curl http:/127.0.0.1:3000/records/birthdate\?file\=spaces.txt

## returns records sorted by last name
curl http:/127.0.0.1:3000/records/name
## To check for values in a certain file (pipes.txt, commas.csv, spaces.txt)
curl http:/127.0.0.1:3000/records/name\?file\=pipes.txt

## POST a new line in the spaces.txt file
curl -d 'new-line=lastName firstName email favoriteColor 2/2/2000' http:/127.0.0.1:3000/records
## POST a new line in the commas.csv file
curl -d 'new-line=lastName firstName, email, favoriteColor, 2/2/2000' http:/127.0.0.1:3000/records
## POST a new line in the pipes.txt file
curl -d 'new-line=lastName firstName | email | favoriteColor | 2/2/2000' http:/127.0.0.1:3000/records
```
