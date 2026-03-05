package com.storytoys.disney.pixar.coloring.princess.googlep.data.local.source

import com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.model.Exercise
import com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.model.enums.Equipment
import com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.model.enums.ExperienceLevel
import com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.model.enums.MuscleGroup
import com.storytoys.disney.pixar.coloring.princess.googlep.core.domain.model.enums.WorkoutPhase
class ExerciseDataSource {

    val allExercises: List<Exercise> get() = warmUpExercises + mainExercises + cooldownExercises

    val warmUpExercises: List<Exercise> = listOf(
        Exercise(
            id = "wu_001", name = "Jumping Jacks", description = "Full body warm-up movement",
            muscleGroups = listOf(MuscleGroup.FULL_BODY), requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.WARM_UP, durationSeconds = 60, defaultSets = 1,
            instructionSteps = listOf("Stand with feet together", "Jump feet out while raising arms overhead", "Jump back to start", "Repeat at a steady pace")
        ),
        Exercise(
            id = "wu_002", name = "Arm Circles", description = "Shoulder warm-up",
            muscleGroups = listOf(MuscleGroup.SHOULDERS), requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.WARM_UP, durationSeconds = 30, defaultSets = 1,
            instructionSteps = listOf("Extend arms to sides", "Make small circles forward 15 times", "Reverse direction for 15 times")
        ),
        Exercise(
            id = "wu_003", name = "High Knees", description = "Cardio warm-up for legs",
            muscleGroups = listOf(MuscleGroup.QUADS, MuscleGroup.CORE), requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.WARM_UP, durationSeconds = 45, defaultSets = 1,
            instructionSteps = listOf("Stand tall", "Lift knees alternately to hip height", "Pump arms in rhythm", "Maintain quick pace")
        ),
        Exercise(
            id = "wu_004", name = "Hip Circles", description = "Hip joint mobility",
            muscleGroups = listOf(MuscleGroup.GLUTES, MuscleGroup.CORE), requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.WARM_UP, durationSeconds = 30, defaultSets = 1,
            instructionSteps = listOf("Hands on hips", "Rotate hips in large circle", "Do 10 each direction")
        ),
        Exercise(
            id = "wu_005", name = "Leg Swings", description = "Hip flexor and hamstring warm-up",
            muscleGroups = listOf(MuscleGroup.HAMSTRINGS, MuscleGroup.GLUTES), requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.WARM_UP, durationSeconds = 30, defaultSets = 1,
            instructionSteps = listOf("Hold wall for balance", "Swing one leg forward and back", "15 reps each leg")
        ),
        Exercise(
            id = "wu_006", name = "Shoulder Rolls", description = "Upper body warm-up",
            muscleGroups = listOf(MuscleGroup.SHOULDERS, MuscleGroup.BACK), requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.WARM_UP, durationSeconds = 20, defaultSets = 1,
            instructionSteps = listOf("Roll shoulders forward 10 times", "Roll shoulders backward 10 times")
        ),
        Exercise(
            id = "wu_007", name = "Inchworm", description = "Full body mobility warm-up",
            muscleGroups = listOf(MuscleGroup.FULL_BODY), requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.WARM_UP, durationSeconds = 60, defaultSets = 1,
            instructionSteps = listOf("Stand tall", "Hinge at hips, walk hands to plank", "Walk feet to hands", "Stand and repeat")
        ),
        Exercise(
            id = "wu_008", name = "Butt Kicks", description = "Leg warm-up with cardio",
            muscleGroups = listOf(MuscleGroup.HAMSTRINGS, MuscleGroup.CALVES), requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.WARM_UP, durationSeconds = 45, defaultSets = 1,
            instructionSteps = listOf("Jog in place", "Kick heels toward glutes alternately", "Keep torso upright")
        )
    )

    val mainExercises: List<Exercise> = listOf(
        // CHEST - Bodyweight
        Exercise(
            id = "m_001", name = "Push-Ups", description = "Classic chest builder",
            muscleGroups = listOf(MuscleGroup.CHEST, MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 12,
            instructionSteps = listOf("Place hands shoulder-width apart", "Lower chest to floor", "Push back up fully", "Keep core tight throughout")
        ),
        Exercise(
            id = "m_002", name = "Diamond Push-Ups", description = "Tricep-focused push-up",
            muscleGroups = listOf(MuscleGroup.CHEST, MuscleGroup.TRICEPS),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 10,
            instructionSteps = listOf("Form diamond with hands under chest", "Keep elbows close to body", "Lower and push up")
        ),
        Exercise(
            id = "m_003", name = "Dumbbell Bench Press", description = "Chest mass builder",
            muscleGroups = listOf(MuscleGroup.CHEST, MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS),
            requiredEquipment = listOf(Equipment.DUMBBELLS, Equipment.BENCH),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.MAIN, defaultSets = 4, defaultReps = 10,
            instructionSteps = listOf("Lie on bench with dumbbells at chest", "Press dumbbells up until arms extended", "Lower slowly to chest", "Keep feet flat on floor")
        ),
        Exercise(
            id = "m_004", name = "Dumbbell Flyes", description = "Chest isolation",
            muscleGroups = listOf(MuscleGroup.CHEST),
            requiredEquipment = listOf(Equipment.DUMBBELLS),
            experienceLevels = listOf(ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 12,
            instructionSteps = listOf("Lie flat, dumbbells above chest", "Open arms wide with slight elbow bend", "Squeeze chest to bring back together")
        ),
        // BACK
        Exercise(
            id = "m_005", name = "Pull-Ups", description = "Upper back and bicep builder",
            muscleGroups = listOf(MuscleGroup.BACK, MuscleGroup.BICEPS),
            requiredEquipment = listOf(Equipment.PULL_UP_BAR),
            experienceLevels = listOf(ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 8,
            instructionSteps = listOf("Grip bar wider than shoulders", "Pull chest to bar", "Lower slowly", "Full hang at bottom")
        ),
        Exercise(
            id = "m_006", name = "Chin-Ups", description = "Bicep-focused back exercise",
            muscleGroups = listOf(MuscleGroup.BACK, MuscleGroup.BICEPS),
            requiredEquipment = listOf(Equipment.PULL_UP_BAR),
            experienceLevels = listOf(ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 8,
            instructionSteps = listOf("Grip bar shoulder-width, palms facing you", "Pull chin above bar", "Lower with control")
        ),
        Exercise(
            id = "m_007", name = "Dumbbell Rows", description = "Unilateral back strength",
            muscleGroups = listOf(MuscleGroup.BACK, MuscleGroup.BICEPS),
            requiredEquipment = listOf(Equipment.DUMBBELLS),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 12,
            instructionSteps = listOf("Brace on bench with one hand", "Pull dumbbell to hip", "Squeeze back at top", "Lower controlled")
        ),
        Exercise(
            id = "m_008", name = "Superman Hold", description = "Lower back strengthener",
            muscleGroups = listOf(MuscleGroup.BACK, MuscleGroup.GLUTES),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, durationSeconds = 30, defaultSets = 3,
            instructionSteps = listOf("Lie face down", "Simultaneously lift arms and legs", "Hold for 2 seconds", "Lower and repeat")
        ),
        Exercise(
            id = "m_009", name = "Resistance Band Rows", description = "Back pull with band",
            muscleGroups = listOf(MuscleGroup.BACK, MuscleGroup.BICEPS),
            requiredEquipment = listOf(Equipment.RESISTANCE_BANDS),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 15,
            instructionSteps = listOf("Anchor band at chest height", "Pull handles to sides of chest", "Squeeze shoulder blades", "Extend arms back out")
        ),
        // SHOULDERS
        Exercise(
            id = "m_010", name = "Pike Push-Ups", description = "Shoulder press bodyweight",
            muscleGroups = listOf(MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 10,
            instructionSteps = listOf("Form inverted V with body", "Bend elbows lowering head toward floor", "Push back up")
        ),
        Exercise(
            id = "m_011", name = "Dumbbell Shoulder Press", description = "Overhead press for shoulder mass",
            muscleGroups = listOf(MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS),
            requiredEquipment = listOf(Equipment.DUMBBELLS),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.MAIN, defaultSets = 4, defaultReps = 10,
            instructionSteps = listOf("Hold dumbbells at shoulders", "Press overhead until arms extended", "Lower to start", "Keep core tight")
        ),
        Exercise(
            id = "m_012", name = "Dumbbell Lateral Raises", description = "Side deltoid isolation",
            muscleGroups = listOf(MuscleGroup.SHOULDERS),
            requiredEquipment = listOf(Equipment.DUMBBELLS),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 15,
            instructionSteps = listOf("Hold dumbbells at sides", "Raise arms to shoulder height", "Pause briefly", "Lower controlled")
        ),
        // BICEPS
        Exercise(
            id = "m_013", name = "Dumbbell Curls", description = "Bicep isolation",
            muscleGroups = listOf(MuscleGroup.BICEPS),
            requiredEquipment = listOf(Equipment.DUMBBELLS),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 12,
            instructionSteps = listOf("Hold dumbbells, palms forward", "Curl weight toward shoulder", "Squeeze at top", "Lower slowly")
        ),
        Exercise(
            id = "m_014", name = "Resistance Band Curls", description = "Band bicep exercise",
            muscleGroups = listOf(MuscleGroup.BICEPS),
            requiredEquipment = listOf(Equipment.RESISTANCE_BANDS),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 15,
            instructionSteps = listOf("Stand on band handles facing up", "Curl hands to shoulders", "Lower with control")
        ),
        Exercise(
            id = "m_015", name = "Hammer Curls", description = "Brachialis and bicep builder",
            muscleGroups = listOf(MuscleGroup.BICEPS),
            requiredEquipment = listOf(Equipment.DUMBBELLS),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 12,
            instructionSteps = listOf("Hold dumbbells neutral (thumbs up)", "Curl upward keeping neutral grip", "Lower with control")
        ),
        // TRICEPS
        Exercise(
            id = "m_016", name = "Tricep Dips", description = "Tricep bodyweight exercise",
            muscleGroups = listOf(MuscleGroup.TRICEPS, MuscleGroup.CHEST),
            requiredEquipment = listOf(Equipment.BENCH),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 12,
            instructionSteps = listOf("Hands on bench behind you", "Lower body bending elbows", "Push back up", "Keep back close to bench")
        ),
        Exercise(
            id = "m_017", name = "Overhead Tricep Extension", description = "Tricep stretch and squeeze",
            muscleGroups = listOf(MuscleGroup.TRICEPS),
            requiredEquipment = listOf(Equipment.DUMBBELLS),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 12,
            instructionSteps = listOf("Hold dumbbell overhead with both hands", "Lower behind head bending elbows", "Extend back up")
        ),
        // CORE
        Exercise(
            id = "m_018", name = "Plank", description = "Core stability exercise",
            muscleGroups = listOf(MuscleGroup.CORE),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.MAIN, durationSeconds = 45, defaultSets = 3,
            instructionSteps = listOf("Forearms on ground", "Body in straight line", "Hold position", "Breathe steadily")
        ),
        Exercise(
            id = "m_019", name = "Crunches", description = "Ab exercise",
            muscleGroups = listOf(MuscleGroup.CORE),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 20,
            instructionSteps = listOf("Lie on back knees bent", "Hands behind head", "Curl shoulders off floor", "Lower controlled")
        ),
        Exercise(
            id = "m_020", name = "Bicycle Crunches", description = "Oblique and core exercise",
            muscleGroups = listOf(MuscleGroup.CORE),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 20,
            instructionSteps = listOf("Lie on back", "Alternate bringing elbow to opposite knee", "Keep non-working leg extended", "Controlled rotation")
        ),
        Exercise(
            id = "m_021", name = "Mountain Climbers", description = "Core cardio exercise",
            muscleGroups = listOf(MuscleGroup.CORE, MuscleGroup.FULL_BODY),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.MAIN, durationSeconds = 45, defaultSets = 3,
            instructionSteps = listOf("Start in plank position", "Drive knees toward chest alternately", "Maintain hip level", "Quick rhythm")
        ),
        Exercise(
            id = "m_022", name = "Leg Raises", description = "Lower ab exercise",
            muscleGroups = listOf(MuscleGroup.CORE),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 15,
            instructionSteps = listOf("Lie on back", "Raise straight legs to 90 degrees", "Lower slowly", "Don't let feet touch floor")
        ),
        Exercise(
            id = "m_023", name = "Russian Twists", description = "Rotational core exercise",
            muscleGroups = listOf(MuscleGroup.CORE),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 20,
            instructionSteps = listOf("Sit with knees bent, lean back slightly", "Clasp hands together", "Rotate side to side", "Keep feet off ground for challenge")
        ),
        // QUADS
        Exercise(
            id = "m_024", name = "Squats", description = "Foundational leg exercise",
            muscleGroups = listOf(MuscleGroup.QUADS, MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 15,
            instructionSteps = listOf("Feet shoulder-width apart", "Lower until thighs parallel to floor", "Keep chest up", "Drive through heels to stand")
        ),
        Exercise(
            id = "m_025", name = "Jump Squats", description = "Explosive leg power",
            muscleGroups = listOf(MuscleGroup.QUADS, MuscleGroup.GLUTES),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 12,
            instructionSteps = listOf("Squat down", "Explode upward jumping", "Land softly bending knees", "Immediately squat again")
        ),
        Exercise(
            id = "m_026", name = "Dumbbell Goblet Squat", description = "Weighted squat variation",
            muscleGroups = listOf(MuscleGroup.QUADS, MuscleGroup.GLUTES),
            requiredEquipment = listOf(Equipment.DUMBBELLS),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 12,
            instructionSteps = listOf("Hold dumbbell vertically at chest", "Squat deep between legs", "Elbows inside knees at bottom", "Drive up to start")
        ),
        Exercise(
            id = "m_027", name = "Wall Sit", description = "Quad isometric hold",
            muscleGroups = listOf(MuscleGroup.QUADS),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, durationSeconds = 45, defaultSets = 3,
            instructionSteps = listOf("Back flat against wall", "Slide down until thighs parallel", "Hold position", "Arms at sides or crossed")
        ),
        // HAMSTRINGS
        Exercise(
            id = "m_028", name = "Romanian Deadlift", description = "Hamstring hinge movement",
            muscleGroups = listOf(MuscleGroup.HAMSTRINGS, MuscleGroup.GLUTES, MuscleGroup.BACK),
            requiredEquipment = listOf(Equipment.DUMBBELLS),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 12,
            instructionSteps = listOf("Hold dumbbells in front", "Hinge at hips pushing them back", "Lower weights along legs", "Feel hamstring stretch then stand")
        ),
        Exercise(
            id = "m_029", name = "Glute Bridge", description = "Posterior chain activation",
            muscleGroups = listOf(MuscleGroup.HAMSTRINGS, MuscleGroup.GLUTES),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 15,
            instructionSteps = listOf("Lie on back knees bent", "Drive hips up squeezing glutes", "Hold 1 second at top", "Lower slowly")
        ),
        Exercise(
            id = "m_030", name = "Nordic Curls", description = "Advanced hamstring exercise",
            muscleGroups = listOf(MuscleGroup.HAMSTRINGS),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 6,
            instructionSteps = listOf("Kneel with feet anchored", "Lower body forward slowly", "Use hands to stop at bottom", "Curl back up")
        ),
        // GLUTES
        Exercise(
            id = "m_031", name = "Donkey Kicks", description = "Glute isolation on all fours",
            muscleGroups = listOf(MuscleGroup.GLUTES),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 15,
            instructionSteps = listOf("On hands and knees", "Kick one leg back and up", "Squeeze glute at top", "15 per side")
        ),
        Exercise(
            id = "m_032", name = "Hip Thrusts", description = "Glute mass builder",
            muscleGroups = listOf(MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS),
            requiredEquipment = listOf(Equipment.BENCH),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 15,
            instructionSteps = listOf("Upper back on bench", "Feet flat, knees bent", "Drive hips up to ceiling", "Squeeze glutes hard at top")
        ),
        Exercise(
            id = "m_033", name = "Lateral Band Walks", description = "Glute med activation",
            muscleGroups = listOf(MuscleGroup.GLUTES),
            requiredEquipment = listOf(Equipment.RESISTANCE_BANDS),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 15,
            instructionSteps = listOf("Band around ankles", "Slight squat position", "Step sideways 15 steps", "Return other direction")
        ),
        // CALVES
        Exercise(
            id = "m_034", name = "Calf Raises", description = "Calf isolation exercise",
            muscleGroups = listOf(MuscleGroup.CALVES),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 20,
            instructionSteps = listOf("Stand on edge of step", "Lower heels below step", "Rise onto toes", "Hold briefly at top")
        ),
        Exercise(
            id = "m_035", name = "Jump Rope (Simulated)", description = "Calf and cardio",
            muscleGroups = listOf(MuscleGroup.CALVES, MuscleGroup.FULL_BODY),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, durationSeconds = 60, defaultSets = 3,
            instructionSteps = listOf("Bounce on balls of feet", "Rotate wrists as if holding rope", "Stay light on feet")
        ),
        // FULL BODY
        Exercise(
            id = "m_036", name = "Burpees", description = "Full body cardio exercise",
            muscleGroups = listOf(MuscleGroup.FULL_BODY),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 10,
            instructionSteps = listOf("Start standing", "Drop to plank position", "Do a push-up", "Jump feet to hands", "Jump up with arms overhead")
        ),
        Exercise(
            id = "m_037", name = "Kettlebell Swings", description = "Hip hinge power movement",
            muscleGroups = listOf(MuscleGroup.FULL_BODY, MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS),
            requiredEquipment = listOf(Equipment.KETTLEBELL),
            experienceLevels = listOf(ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 20,
            instructionSteps = listOf("Hinge at hips, swing kettlebell between legs", "Drive hips forward explosively", "Let kettlebell float to shoulder height", "Control descent and hinge back")
        ),
        Exercise(
            id = "m_038", name = "Lunges", description = "Unilateral leg exercise",
            muscleGroups = listOf(MuscleGroup.QUADS, MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 12,
            instructionSteps = listOf("Step forward with one leg", "Lower back knee toward floor", "Front knee over ankle", "Push back to start")
        ),
        Exercise(
            id = "m_039", name = "Dumbbell Lunges", description = "Weighted lunge",
            muscleGroups = listOf(MuscleGroup.QUADS, MuscleGroup.GLUTES),
            requiredEquipment = listOf(Equipment.DUMBBELLS),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 12,
            instructionSteps = listOf("Hold dumbbells at sides", "Step forward into lunge", "Lower back knee", "Drive forward to stand")
        ),
        Exercise(
            id = "m_040", name = "Step-Ups", description = "Single leg power",
            muscleGroups = listOf(MuscleGroup.QUADS, MuscleGroup.GLUTES),
            requiredEquipment = listOf(Equipment.BENCH),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 12,
            instructionSteps = listOf("Face bench/step", "Step up with one foot", "Drive opposite knee up", "Step down and alternate")
        ),
        Exercise(
            id = "m_041", name = "Dumbbell Deadlift", description = "Full posterior chain",
            muscleGroups = listOf(MuscleGroup.BACK, MuscleGroup.HAMSTRINGS, MuscleGroup.GLUTES),
            requiredEquipment = listOf(Equipment.DUMBBELLS),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 12,
            instructionSteps = listOf("Dumbbells in front of thighs", "Hinge pushing hips back", "Lower to mid-shin", "Drive hips forward to stand")
        ),
        Exercise(
            id = "m_042", name = "Box Jumps", description = "Explosive lower body",
            muscleGroups = listOf(MuscleGroup.QUADS, MuscleGroup.GLUTES, MuscleGroup.CALVES),
            requiredEquipment = listOf(Equipment.BENCH),
            experienceLevels = listOf(ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 10,
            instructionSteps = listOf("Stand before bench", "Bend knees and swing arms", "Jump onto bench landing softly", "Step back down")
        ),
        Exercise(
            id = "m_043", name = "Resistance Band Squats", description = "Banded squat variation",
            muscleGroups = listOf(MuscleGroup.QUADS, MuscleGroup.GLUTES),
            requiredEquipment = listOf(Equipment.RESISTANCE_BANDS),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 15,
            instructionSteps = listOf("Stand on band holding handles at shoulders", "Squat to parallel", "Keep knees out against band tension", "Stand back up")
        ),
        Exercise(
            id = "m_044", name = "Tricep Pushdowns (Band)", description = "Tricep isolation with band",
            muscleGroups = listOf(MuscleGroup.TRICEPS),
            requiredEquipment = listOf(Equipment.RESISTANCE_BANDS),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 15,
            instructionSteps = listOf("Anchor band overhead", "Elbows at sides", "Push handles down extending arms", "Control return")
        ),
        Exercise(
            id = "m_045", name = "Inverted Rows", description = "Bodyweight row using bar",
            muscleGroups = listOf(MuscleGroup.BACK, MuscleGroup.BICEPS),
            requiredEquipment = listOf(Equipment.PULL_UP_BAR),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 12,
            instructionSteps = listOf("Bar set at waist height", "Hang underneath bar with straight body", "Pull chest to bar", "Lower slowly")
        ),
        Exercise(
            id = "m_046", name = "Single-Leg Glute Bridge", description = "Unilateral glute work",
            muscleGroups = listOf(MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 12,
            instructionSteps = listOf("Lie on back, one leg extended", "Drive one hip up", "Squeeze glute at top", "12 reps per leg")
        ),
        Exercise(
            id = "m_047", name = "Side Plank", description = "Lateral core stability",
            muscleGroups = listOf(MuscleGroup.CORE),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, durationSeconds = 30, defaultSets = 3,
            instructionSteps = listOf("Lie on side, forearm on ground", "Lift hips forming straight line", "Hold position", "30 seconds per side")
        ),
        Exercise(
            id = "m_048", name = "Dumbbell Front Raises", description = "Anterior deltoid",
            muscleGroups = listOf(MuscleGroup.SHOULDERS),
            requiredEquipment = listOf(Equipment.DUMBBELLS),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 12,
            instructionSteps = listOf("Hold dumbbells at thighs", "Raise arms forward to shoulder height", "Lower slowly")
        ),
        Exercise(
            id = "m_049", name = "Bear Crawl", description = "Full body core stabilizer",
            muscleGroups = listOf(MuscleGroup.FULL_BODY, MuscleGroup.CORE, MuscleGroup.SHOULDERS),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, durationSeconds = 45, defaultSets = 3,
            instructionSteps = listOf("All fours, knees hovering 2 inches off ground", "Move opposite hand and foot forward", "Keep hips low", "Travel 10 meters and back")
        ),
        Exercise(
            id = "m_050", name = "Resistance Band Pull Apart", description = "Rear deltoid and upper back",
            muscleGroups = listOf(MuscleGroup.SHOULDERS, MuscleGroup.BACK),
            requiredEquipment = listOf(Equipment.RESISTANCE_BANDS),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 15,
            instructionSteps = listOf("Hold band in front at shoulder width", "Pull band apart to chest level", "Squeeze shoulder blades", "Return to start")
        ),
        Exercise(
            id = "m_051", name = "Decline Push-Ups", description = "Upper chest push-up variation",
            muscleGroups = listOf(MuscleGroup.CHEST, MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS),
            requiredEquipment = listOf(Equipment.BENCH),
            experienceLevels = listOf(ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 12,
            instructionSteps = listOf("Feet on bench, hands on floor", "Lower chest toward floor", "Push back up", "Keep body straight")
        ),
        Exercise(
            id = "m_052", name = "Wide-Grip Push-Ups", description = "Chest-focused push-up",
            muscleGroups = listOf(MuscleGroup.CHEST, MuscleGroup.TRICEPS),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE),
            phase = WorkoutPhase.MAIN, defaultSets = 3, defaultReps = 12,
            instructionSteps = listOf("Hands wider than shoulders", "Lower chest to floor", "Keep elbows flared 45 degrees", "Push back up")
        )
    )

    val cooldownExercises: List<Exercise> = listOf(
        Exercise(
            id = "cd_001", name = "Child's Pose", description = "Lower back and hip stretch",
            muscleGroups = listOf(MuscleGroup.BACK, MuscleGroup.GLUTES),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.COOLDOWN, durationSeconds = 45, defaultSets = 1,
            instructionSteps = listOf("Kneel and sit back on heels", "Reach arms forward on floor", "Relax and breathe deeply")
        ),
        Exercise(
            id = "cd_002", name = "Standing Quad Stretch", description = "Quad flexibility",
            muscleGroups = listOf(MuscleGroup.QUADS),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.COOLDOWN, durationSeconds = 30, defaultSets = 1,
            instructionSteps = listOf("Stand on one leg", "Pull other foot to glute", "Hold 30 seconds per side")
        ),
        Exercise(
            id = "cd_003", name = "Hamstring Stretch", description = "Posterior leg flexibility",
            muscleGroups = listOf(MuscleGroup.HAMSTRINGS),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.COOLDOWN, durationSeconds = 30, defaultSets = 1,
            instructionSteps = listOf("Sit on floor", "Extend one leg", "Reach toward toes", "Hold 30 seconds per leg")
        ),
        Exercise(
            id = "cd_004", name = "Chest Opener", description = "Chest and shoulder stretch",
            muscleGroups = listOf(MuscleGroup.CHEST, MuscleGroup.SHOULDERS),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.COOLDOWN, durationSeconds = 30, defaultSets = 1,
            instructionSteps = listOf("Clasp hands behind back", "Open chest looking up", "Breathe and hold 30 seconds")
        ),
        Exercise(
            id = "cd_005", name = "Pigeon Pose", description = "Deep hip stretch",
            muscleGroups = listOf(MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS),
            requiredEquipment = listOf(Equipment.YOGA_MAT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.COOLDOWN, durationSeconds = 45, defaultSets = 1,
            instructionSteps = listOf("From plank, bring knee to wrist", "Extend back leg behind", "Lower torso forward", "Hold 45 seconds each side")
        ),
        Exercise(
            id = "cd_006", name = "Shoulder Cross Stretch", description = "Shoulder flexibility",
            muscleGroups = listOf(MuscleGroup.SHOULDERS),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.COOLDOWN, durationSeconds = 20, defaultSets = 1,
            instructionSteps = listOf("Bring one arm across chest", "Use other hand to press gently", "Hold 20 seconds per arm")
        ),
        Exercise(
            id = "cd_007", name = "Cat-Cow Stretch", description = "Spinal mobility",
            muscleGroups = listOf(MuscleGroup.BACK, MuscleGroup.CORE),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.COOLDOWN, durationSeconds = 45, defaultSets = 1,
            instructionSteps = listOf("On all fours", "Arch back up (cat)", "Sag back down (cow)", "Breathe through 10 cycles")
        ),
        Exercise(
            id = "cd_008", name = "Calf Stretch", description = "Calf and Achilles flexibility",
            muscleGroups = listOf(MuscleGroup.CALVES),
            requiredEquipment = listOf(Equipment.NO_EQUIPMENT),
            experienceLevels = listOf(ExperienceLevel.BEGINNER, ExperienceLevel.INTERMEDIATE, ExperienceLevel.ADVANCED),
            phase = WorkoutPhase.COOLDOWN, durationSeconds = 30, defaultSets = 1,
            instructionSteps = listOf("Step back with one foot", "Press back heel down", "Lean into wall/support", "Hold 30 seconds per leg")
        )
    )
}
