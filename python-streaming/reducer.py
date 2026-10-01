#!/usr/bin/env python3
import sys

# Reducer function to group student records by academicLevel
def reducer():
    current_level = None
    students = []

    for line in sys.stdin:
        line = line.strip()
        academic_level, student_name = line.split("\t")

        if current_level != academic_level:
            if current_level is not None:
                # Print all students for the current academicLevel
                for student in students:
                    print(f"{current_level}\t{student}")
            current_level = academic_level
            students = []
        
        students.append(student_name)

    # Output remaining students
    if current_level is not None:
        for student in students:
            print(f"{current_level}\t{student}")

if __name__ == "__main__":
    reducer()
