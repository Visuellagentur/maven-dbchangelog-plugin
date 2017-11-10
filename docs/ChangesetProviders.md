# Changeset Providers


## Schema Changes

Changes to the schema do not occur frequently, but when they do, they must be addressed in a
way that 

* each developer works in a dedicated sandbox which is a replica of the daily environment
* citizens of other environments must never be disturbed

Therefore

* no more direct changes to the schema, including creating and setting up databases, in daily
* what now feels as if it will induce additional workload on the individual will soon become a relief to all involved

For the team this means that

* we need to figure out how to replicate existing data from daily to the dedicated local replicas
* how to automate the process of applying changes to the schema including migration of the existing data

Furthermore

* creating more and more databases in order to not disturb other citizens of the environment does not work
  and will soon become unmanageable, especially when each developer follows his or her own scheme
* in the future, existing databases need to be merged into one in order to optimize overall database access patterns
  by establishing missing foreign key relationships, constraints and other indexes
  to support an overhaul of the existing architecture, which has its roots in lplus/aid@
  in order to become more agile in the overall process
  in order to increase the performance of the application
* we need to eliminate these fucking custom in memory caches as they do not work and make the application
  a bad citizen of its environment (memory usage, cpu usage, bandwidth usage) (hog in a sty anti pattern)
* we need to move all entities to jpa/hibernate
* we can then introduce well tested and well performing in memory caches in order to speed things up again


### Developers





## Content Change Providers





### TTMT


### Pricing



#### Long Term Action

A tool must be established here in order to become independent of AID@.



### Developers


### Change Managers


