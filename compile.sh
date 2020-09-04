#!/bin/bash
# Script to mount submitted files then run compiler container

# run container, mounting files to compile as read-only
# detach to allow script to continue to timer
container=$(docker run -d -v $1:/root/compile/Files:ro mount_compiler)

# waits 10s and if no output from container, set to true.
# otherwise wait returns docker exit code
result=$(timeout "10" docker wait "$container" || true)

# if timeout has occured kill container and 
# print notification to error stream
if [ -z "$result" ];
then
    #kill the container
    docker kill $container &> /dev/null

    # remove container, prevent output printing
    docker rm $container &> /dev/null

    # print to stderr that timeout occured
    echo Execution timed out >&2
# else get container output from log
else
    #get results from log
    output=$(docker logs $container)
    
    # remove container, prevent output printing
    docker rm $container &> /dev/null

    # echo results
    echo $output
fi