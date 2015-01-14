This program calculates the concurrencies of two books bought together in an online bookstore using the MapReduce tool of the hadoop environment.
It assumes that the books b1,b2,… are bought by users u1,u2,… and the purchases are stored in files (hourly):
'user','b1','b2','b3'
'u1’,b1,b2,b3
'u2’,b1
‘u3’,b2,b3
…
Then the mapreduce job returns an unstructured cooccurrence matrix:
‘b1’,’b2’  2
‘b1’,’b2’  4
‘b1’,’b2’  3
….

from which the total cooccurrence matrix can be updated and the books recommender (see cold-start-recommender git branch) can be employed to propose books to the users.

