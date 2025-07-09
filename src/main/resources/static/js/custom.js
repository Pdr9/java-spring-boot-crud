// JavaScript customizado para o Sistema de Produtos Favoritos

document.addEventListener('DOMContentLoaded', function() {
    
    // Inicializar tooltips do Bootstrap
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // Confirmação de exclusão
    const deleteButtons = document.querySelectorAll('.btn-delete');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            
            const itemName = this.dataset.itemName || 'este item';
            const itemType = this.dataset.itemType || 'item';
            
            if (confirm(`Tem certeza que deseja excluir ${itemType} "${itemName}"?`)) {
                // Submeter o formulário de exclusão
                const form = this.closest('form');
                if (form) {
                    form.submit();
                } else {
                    // Se não há formulário, fazer requisição direta
                    const url = this.dataset.deleteUrl || this.href;
                    if (url) {
                        window.location.href = url;
                    }
                }
            }
        });
    });

    // Auto-hide alerts depois de 5 segundos
    const alerts = document.querySelectorAll('.alert:not(.alert-permanent)');
    alerts.forEach(alert => {
        setTimeout(() => {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        }, 5000);
    });

    // Pesquisa em tempo real (debounced)
    const searchInputs = document.querySelectorAll('.search-input');
    searchInputs.forEach(input => {
        let timeout;
        input.addEventListener('input', function() {
            clearTimeout(timeout);
            timeout = setTimeout(() => {
                performSearch(this.value);
            }, 500);
        });
    });

    // Filtros automáticos
    const filterSelects = document.querySelectorAll('.auto-filter');
    filterSelects.forEach(select => {
        select.addEventListener('change', function() {
            applyFilters();
        });
    });

    // Preview de imagem em formulários
    const imageInputs = document.querySelectorAll('input[type="file"][accept*="image"]');
    imageInputs.forEach(input => {
        input.addEventListener('change', function(e) {
            const file = e.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    const preview = document.getElementById('image-preview');
                    if (preview) {
                        preview.src = e.target.result;
                        preview.style.display = 'block';
                    }
                };
                reader.readAsDataURL(file);
            }
        });
    });

    // Validação de URL
    const urlInputs = document.querySelectorAll('input[type="url"]');
    urlInputs.forEach(input => {
        input.addEventListener('blur', function() {
            if (this.value && !isValidUrl(this.value)) {
                this.classList.add('is-invalid');
                showFieldError(this, 'Por favor, insira uma URL válida');
            } else {
                this.classList.remove('is-invalid');
                hideFieldError(this);
            }
        });
    });

    // Formatação de preço
    const priceInputs = document.querySelectorAll('input[step="0.01"]');
    priceInputs.forEach(input => {
        input.addEventListener('blur', function() {
            if (this.value) {
                const value = parseFloat(this.value);
                if (!isNaN(value)) {
                    this.value = value.toFixed(2);
                }
            }
        });
    });

    // Animação de fade-in para elementos
    const observerOptions = {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
    };

    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('fade-in');
                observer.unobserve(entry.target);
            }
        });
    }, observerOptions);

    const animatedElements = document.querySelectorAll('.card, .stats-card, .table');
    animatedElements.forEach(el => observer.observe(el));
});

// Função para realizar pesquisa
function performSearch(query) {
    const currentUrl = new URL(window.location);
    
    if (query.trim()) {
        currentUrl.searchParams.set('search', query);
    } else {
        currentUrl.searchParams.delete('search');
    }
    
    // Resetar página para 1 ao pesquisar
    currentUrl.searchParams.delete('page');
    
    window.location.href = currentUrl.toString();
}

// Função para aplicar filtros
function applyFilters() {
    const currentUrl = new URL(window.location);
    
    // Coletar todos os filtros
    const filters = {};
    document.querySelectorAll('.auto-filter').forEach(filter => {
        if (filter.value) {
            filters[filter.name] = filter.value;
        }
    });
    
    // Limpar parâmetros antigos de filtro
    ['userId', 'categoryId', 'status', 'sort'].forEach(param => {
        currentUrl.searchParams.delete(param);
    });
    
    // Adicionar novos filtros
    Object.entries(filters).forEach(([key, value]) => {
        currentUrl.searchParams.set(key, value);
    });
    
    // Resetar página para 1 ao filtrar
    currentUrl.searchParams.delete('page');
    
    window.location.href = currentUrl.toString();
}

// Função para validar URL
function isValidUrl(string) {
    try {
        new URL(string);
        return true;
    } catch (_) {
        return false;
    }
}

// Função para mostrar erro de campo
function showFieldError(field, message) {
    hideFieldError(field); // Remove erro anterior se existir
    
    const errorDiv = document.createElement('div');
    errorDiv.className = 'invalid-feedback';
    errorDiv.textContent = message;
    
    field.parentNode.appendChild(errorDiv);
}

// Função para esconder erro de campo
function hideFieldError(field) {
    const existingError = field.parentNode.querySelector('.invalid-feedback');
    if (existingError) {
        existingError.remove();
    }
}

// Função para mostrar loading
function showLoading(element) {
    const originalContent = element.innerHTML;
    element.disabled = true;
    element.innerHTML = '<span class="spinner-border spinner-border-sm me-2" role="status"></span>Carregando...';
    element.dataset.originalContent = originalContent;
}

// Função para esconder loading
function hideLoading(element) {
    element.disabled = false;
    element.innerHTML = element.dataset.originalContent || element.innerHTML;
}

// Função para copiar texto para clipboard
async function copyToClipboard(text) {
    try {
        await navigator.clipboard.writeText(text);
        showToast('Texto copiado para a área de transferência!', 'success');
    } catch (err) {
        console.error('Erro ao copiar texto: ', err);
        showToast('Erro ao copiar texto', 'error');
    }
}

// Função para mostrar toast notifications
function showToast(message, type = 'info') {
    const toastContainer = document.getElementById('toast-container') || createToastContainer();
    
    const toastId = 'toast-' + Date.now();
    const toastHtml = `
        <div id="${toastId}" class="toast align-items-center text-white bg-${type === 'error' ? 'danger' : type} border-0" role="alert">
            <div class="d-flex">
                <div class="toast-body">
                    ${message}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
            </div>
        </div>
    `;
    
    toastContainer.insertAdjacentHTML('beforeend', toastHtml);
    const toastElement = document.getElementById(toastId);
    const toast = new bootstrap.Toast(toastElement);
    
    toast.show();
    
    // Remove o toast do DOM após ser fechado
    toastElement.addEventListener('hidden.bs.toast', () => {
        toastElement.remove();
    });
}

// Função para criar container de toasts
function createToastContainer() {
    const container = document.createElement('div');
    container.id = 'toast-container';
    container.className = 'toast-container position-fixed top-0 end-0 p-3';
    container.style.zIndex = '1055';
    document.body.appendChild(container);
    return container;
}

// Função para formatar moeda brasileira
function formatCurrency(value) {
    return new Intl.NumberFormat('pt-BR', {
        style: 'currency',
        currency: 'BRL'
    }).format(value);
}

// Função para formatar data
function formatDate(dateString) {
    const date = new Date(dateString);
    return new Intl.DateTimeFormat('pt-BR', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    }).format(date);
}

// Exportar funções para uso global
window.FavoriteProducts = {
    performSearch,
    applyFilters,
    showLoading,
    hideLoading,
    copyToClipboard,
    showToast,
    formatCurrency,
    formatDate
}; 