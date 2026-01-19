# Vibes - Clojure TDD/BDD Project

## TDD Workflow (Strict)

**Always follow test-first development with maximum incrementalism.**

### The Red-Green-Refactor Cycle

1. **Red** - Write a *single* failing test (one scenario or step)
2. **Green** - Write the *minimal* code to make the test pass
3. **Refactor** - Clean up while keeping tests green
4. **Repeat** - Go back to step 1 for the next behavior

### Critical Rules

- **One test at a time** - Never write multiple failing tests. Write one, make it pass, then write the next.
- **Minimal implementation** - Only write enough production code to make the current test pass. Do not anticipate future tests.
- **No code without a failing test** - Every line of production code must be justified by a failing test.
- **Small steps** - If a step feels too big, break it down further. Smaller is always better.
- **Run tests constantly** - Run the test suite after every change to verify you're on track.

### Example Cycle

```
1. Write: Scenario step "Given a user named Alice"
2. Run tests → RED (step not defined)
3. Write: Step definition that stores name in state
4. Run tests → GREEN
5. Refactor if needed
6. Write: Next step "When Alice logs in"
7. Run tests → RED
8. Write: Minimal login logic
9. Run tests → GREEN
... and so on
```

The goal is a continuous series of tiny, verified increments—not big batches of code.

## Gherkin/BDD Testing

This project uses **kaocha-cucumber** for BDD-style testing with Gherkin feature files.

### Feature File Format

```gherkin
Feature: Brief description of the feature

  Scenario: Specific behavior being tested
    Given some initial context
    When an action is performed
    Then the expected outcome occurs

  Scenario Outline: Parameterized test
    Given a value of <input>
    When processed
    Then the result is <output>

    Examples:
      | input | output |
      | 1     | 2      |
      | 5     | 10     |
```

### Step Definitions

Step definitions map Gherkin steps to Clojure code:

```clojure
(require '[lambdaisland.cucumber.dsl :refer :all])

(Given "some initial context" [state]
  (assoc state :initialized true))

(When "an action is performed" [state]
  (assoc state :result (some-fn (:initialized state))))

(Then "the expected outcome occurs" [state]
  (assert (= expected (:result state)))
  state)
```

## Project Structure

```
vibes/
├── deps.edn              # Project dependencies
├── tests.edn             # Kaocha test configuration
├── .cljfmt.edn           # Formatter configuration
├── src/
│   └── vibes/
│       └── core.clj      # Main source code
├── test/
│   ├── features/         # Gherkin .feature files
│   │   └── *.feature
│   └── step_definitions/ # Step definition .clj files
│       └── *_steps.clj
```

## Dependencies (deps.edn)

```clojure
{:deps {}

 :aliases
 {:test {:extra-paths ["test" "test/step_definitions"]
         :extra-deps {lambdaisland/kaocha {:mvn/version "1.91.1392"}
                      lambdaisland/kaocha-cucumber {:mvn/version "0.11.100"}}
         :main-opts ["-m" "kaocha.runner"]}

  :format {:extra-deps {dev.weavejester/cljfmt {:mvn/version "0.13.0"}}
           :main-opts ["-m" "cljfmt.main" "fix" "src" "test"]}

  :format-check {:extra-deps {dev.weavejester/cljfmt {:mvn/version "0.13.0"}}
                 :main-opts ["-m" "cljfmt.main" "check" "src" "test"]}}}
```

## Test Configuration (tests.edn)

```clojure
#kaocha/v1
{:tests [{:id :unit
          :type :kaocha.type/cucumber
          :source-paths ["src"]
          :test-paths ["test/features"]
          :cucumber/glue-paths ["test/step_definitions"]}]}
```

## Commands

```bash
# Run all tests
clj -M:test

# Run tests in watch mode
clj -M:test --watch

# Run specific feature
clj -M:test --focus :unit --cucumber-filter "Feature Name"

# Format all Clojure files
clj -M:format

# Check formatting (CI-friendly, doesn't modify files)
clj -M:format-check
```

## Commit Style

Follow conventional commit format:

```
<type>(<scope>): <description>

[optional body]
```

Types: `feat`, `fix`, `test`, `refactor`, `docs`, `chore`

Examples:
- `feat(core): add vibration frequency calculator`
- `test(frequency): add scenarios for edge cases`
- `fix(parser): handle empty input gracefully`
