#!/usr/bin/env sh

cache_dir=.cache
gjf=google-java-format-1.6-all-deps.jar
gjf_link=https://github.com/google/google-java-format/releases/download/google-java-format-1.6/$gjf

echo "Running formatting script..."

mkdir -p $cache_dir && cd $cache_dir
if [ ! -f $gjf ]
then
	echo "Downloading $gjf..."
    curl -LJO $gjf_link
    chmod 644 $gjf
fi
cd ..

changed_java_files=$(git diff --cached --name-only --diff-filter=ACMR | grep ".*java$" )
if [ "${changed_java_files:+1}" ]
then
	echo "Applying formatting to:"
	echo "$changed_java_files"
	java -jar .cache/$gjf --replace $changed_java_files
	git add $changed_java_files
fi
