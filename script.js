document.addEventListener('DOMContentLoaded', () => {
    const input = document.querySelector('input');
    const button = document.querySelector('.Send');
    const container = document.querySelector('.container');
    const flashcardsDiv = document.getElementById('flashcards');

    button.addEventListener('click', async () => {
        const topic = input.value.trim();
        if (!topic) return;

        try {
            const response = await fetch('/generate', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ topic })
            });

            if (!response.ok) throw new Error('Failed to generate flashcards');

            const data = await response.json();
            const flashcards = data.flashcards || [];

            displayFlashcards(flashcards);
        } catch (error) {
            console.error('Error:', error);
            // For demo, use mock data
            const mockFlashcards = [
                { question: 'What is the capital of France?', answer: 'Paris' },
                { question: 'What is 2 + 2?', answer: '4' },
                { question: 'What is the color of the sky?', answer: 'Blue' }
            ];
            displayFlashcards(mockFlashcards);
        }
    });

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
