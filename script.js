document.addEventListener('DOMContentLoaded', () => {
    const input = document.querySelector('input');
    const button = document.querySelector('.Send');
    const container = document.querySelector('.container');
    const flashcardsDiv = document.getElementById('flashcards');

    // Handle topic button clicks
    const topicButtons = document.querySelectorAll('.topic-btn');
    topicButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            const topic = btn.getAttribute('data-topic');
            input.value = topic;
            button.click(); // Automatically generate flashcards for the selected topic
        });
    });

    button.addEventListener('click', async () => {
        const topic = input.value.trim();
        if (!topic) return;

        try {
            // Call the Java backend API
            const response = await fetch(`http://localhost:8080/generate?message=${encodeURIComponent(topic)}`);
            
            if (!response.ok) throw new Error('Failed to generate flashcards');
            
            const responseText = await response.text();
            
            // Parse the AI response and extract flashcards
            const flashcards = parseAIResponse(responseText);
            displayFlashcards(flashcards);
        } catch (error) {
            console.error('Error:', error);
            alert('Error generating flashcards. Please try again.');
        }
    });

    function parseAIResponse(response) {
        try {
            // Parse the response as JSON
            const data = JSON.parse(response);
            
            // Backend now returns a clean array of flashcards
            if (Array.isArray(data)) {
                return data.map(card => ({
                    question: card.concept || card.question || 'Question',
                    answer: card.definition || card.answer || 'Answer'
                }));
            }
            
            // Fallback for other formats
            return [
                { question: 'Flashcard', answer: 'Unable to parse flashcard data' }
            ];
        } catch (error) {
            console.error('Error parsing response:', error);
            return [
                { question: 'Error', answer: 'Could not parse flashcard data' }
            ];
        }
    }

    function generateMockFlashcards(topic) {
        return [
            { question: `What is ${topic}?`, answer: `${topic} is a topic you want to study.` },
            { question: `Why study ${topic}?`, answer: `Studying ${topic} helps you gain knowledge and skills.` },
            { question: `Key concepts in ${topic}`, answer: `Some key concepts include basics, advanced topics, and applications.` }
        ];
    }

    function displayFlashcards(flashcards) {
        container.classList.add('hidden');
        flashcardsDiv.classList.remove('hidden');

        flashcardsDiv.innerHTML = '';

        flashcards.forEach(card => {
            const flashcardDiv = document.createElement('div');
            flashcardDiv.className = 'flashcard';

            flashcardDiv.innerHTML = `
                <div class="flashcard-inner">
                    <div class="flashcard-front">
                        <p>${card.question}</p>
                    </div>
                    <div class="flashcard-back">
                        <p>${card.answer}</p>
                    </div>
                </div>
            `;

            flashcardDiv.addEventListener('click', () => {
                flashcardDiv.classList.toggle('flipped');
            });

            flashcardsDiv.appendChild(flashcardDiv);
        });
    }
});
