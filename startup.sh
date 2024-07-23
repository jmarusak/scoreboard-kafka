#!/bin/bash

# Define the scripts to run
SCRIPTS=("scoreboard.sh" "scorecard.sh")

# Loop through each script and start a tmux session for it
for script in "${SCRIPTS[@]}"; do
    # Extract the script name without the extension to use as the tmux session name
    session_name="${script%.sh}"

    # Start the script in a detached tmux session
    tmux new -d -s "$session_name" "bash $script"

    echo "Started $script in tmux session: $session_name"
done
