#!/bin/bash
#         __ __ __                        __ 
# .-----.|__|  |  |--.----.-----.---.-.--|  |
# |__ --||  |  |    <|   _|  _  |  _  |  _  |
# |_____||__|__|__|__|__| |_____|___._|_____|
#
# removes all the bs from the project...
#

find . -type f \( -name "*.class" -o -name "*.ctxt" -o -name "*~" -o -name ".*~" -o -name ".*.swp" \) -exec rm -f {} +
