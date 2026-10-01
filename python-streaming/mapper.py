#!/usr/bin/env python3
import sys

# Mapper function to output academicLevel as key and student record as value
def mapper():
    for line in sys.stdin:
        line = line.strip()
        fields = line.split(",")
        if len(fields) == 2:
            student_name = fields[0].strip()
            academic_level = fields[1].strip()
            print(f"{academic_level}\t{student_name}")

if __name__ == "__main__":
    mapper()
