// ============================================
// DADOS MOCKADOS (simulando o backend)
// ============================================

const MOCK_DATA = {
    users: [
        { id: 1, username: 'admin', email: 'admin@prompthub.com', role: 'ADMIN' },
        { id: 2, username: 'joao.silva', email: 'joao.silva@email.com', role: 'USER' },
        { id: 3, username: 'maria.santos', email: 'maria.santos@email.com', role: 'USER' },
        { id: 4, username: 'pedro.costa', email: 'pedro.costa@email.com', role: 'USER' },
        { id: 5, username: 'ana.oliveira', email: 'ana.oliveira@email.com', role: 'USER' },
        { id: 6, username: 'carlos.lima', email: 'carlos.lima@email.com', role: 'USER' }
    ],
    
    teams: [
        { id: 1, name: 'Time Backend', description: 'Equipe responsável pelo desenvolvimento backend', status: 'ACTIVE' },
        { id: 2, name: 'Time Frontend', description: 'Equipe responsável pelo desenvolvimento frontend', status: 'ACTIVE' },
        { id: 3, name: 'Time DevOps', description: 'Equipe responsável por infraestrutura e CI/CD', status: 'ACTIVE' },
        { id: 4, name: 'Time QA', description: 'Equipe de qualidade e testes', status: 'ACTIVE' }
    ],
    
    teamUsers: [
        // Time Backend
        { teamId: 1, userId: 2, role: 'TEAM_OWNER' },
        { teamId: 1, userId: 3, role: 'DEV' },
        { teamId: 1, userId: 4, role: 'VIEWER' },
        // Time Frontend
        { teamId: 2, userId: 5, role: 'TEAM_OWNER' },
        { teamId: 2, userId: 3, role: 'DEV' },
        { teamId: 2, userId: 6, role: 'DEV' },
        // Time DevOps
        { teamId: 3, userId: 6, role: 'TEAM_OWNER' },
        { teamId: 3, userId: 4, role: 'DEV' },
        // Time QA
        { teamId: 4, userId: 5, role: 'TEAM_OWNER' },
        { teamId: 4, userId: 2, role: 'VIEWER' }
    ],
    
    prompts: [
        // Time Backend
        { id: 1, teamId: 1, ownerId: null, name: 'Análise de Código Java', description: 'Prompt para revisar código Java e sugerir melhorias' },
        { id: 2, teamId: 1, ownerId: null, name: 'Geração de Testes Unitários', description: 'Prompt para gerar testes unitários automaticamente' },
        { id: 3, teamId: 1, ownerId: null, name: 'Documentação de API', description: 'Prompt para gerar documentação de endpoints REST' },
        // Time Frontend
        { id: 4, teamId: 2, ownerId: null, name: 'Componente React', description: 'Prompt para criar componentes React com TypeScript' },
        { id: 5, teamId: 2, ownerId: null, name: 'Validação de Formulário', description: 'Prompt para criar validações de formulário' },
        // Time DevOps
        { id: 6, teamId: 3, ownerId: null, name: 'Pipeline CI/CD', description: 'Prompt para criar pipelines de CI/CD' },
        { id: 7, teamId: 3, ownerId: null, name: 'Dockerfile Otimizado', description: 'Prompt para criar Dockerfiles otimizados' },
        // Time QA
        { id: 8, teamId: 4, ownerId: null, name: 'Casos de Teste', description: 'Prompt para gerar casos de teste' },
        { id: 9, teamId: 4, ownerId: null, name: 'Testes E2E', description: 'Prompt para criar testes end-to-end' },
        // Pessoais
        { id: 10, teamId: null, ownerId: 2, name: 'Meu Prompt de Estudo', description: 'Prompt pessoal para estudos de algoritmos' },
        { id: 11, teamId: null, ownerId: 2, name: 'Refatoração Pessoal', description: 'Prompt para refatorar meus códigos pessoais' },
        { id: 12, teamId: null, ownerId: 3, name: 'Aprendizado de Design Patterns', description: 'Prompt para estudar padrões de projeto' },
        { id: 13, teamId: null, ownerId: 4, name: 'Otimização de Queries', description: 'Prompt para otimizar queries SQL' },
        { id: 14, teamId: null, ownerId: 5, name: 'Code Review Pessoal', description: 'Prompt para revisar meus próprios códigos' },
        { id: 15, teamId: null, ownerId: 6, name: 'Kubernetes Helper', description: 'Prompt para ajudar com configurações Kubernetes' }
    ],
    
    promptVersions: [
        // Versões do prompt "Análise de Código Java" (id: 1)
        { id: 1, promptId: 1, version: 1, content: 'Analise o seguinte código Java e sugira melhorias...', description: 'Versão inicial', isPublic: true, category: 'backend', createdBy: 2, createdAt: '2024-01-15' },
        { id: 2, promptId: 1, version: 2, content: 'Analise o código Java abaixo considerando SOLID e Clean Code...', description: 'Adicionado foco em SOLID', isPublic: true, category: 'backend', createdBy: 2, createdAt: '2024-02-01' },
        
        // Versões do prompt "Componente React" (id: 4)
        { id: 3, promptId: 4, version: 1, content: 'Crie um componente React funcional com TypeScript...', description: 'Versão inicial', isPublic: true, category: 'frontend', createdBy: 5, createdAt: '2024-01-20' },
        { id: 4, promptId: 4, version: 2, content: 'Crie um componente React funcional com TypeScript, incluindo testes...', description: 'Adicionado requisito de testes', isPublic: false, category: 'frontend', createdBy: 5, createdAt: '2024-02-10' },
        
        // Versões do prompt "Pipeline CI/CD" (id: 6)
        { id: 5, promptId: 6, version: 1, content: 'Crie um pipeline CI/CD usando GitHub Actions...', description: 'Versão inicial', isPublic: true, category: 'devops', createdBy: 6, createdAt: '2024-01-10' },
        { id: 6, promptId: 6, version: 2, content: 'Crie um pipeline CI/CD completo com build, test e deploy...', description: 'Pipeline completo', isPublic: true, category: 'devops', createdBy: 6, createdAt: '2024-02-05' },
        
        // Versões do prompt "Casos de Teste" (id: 8)
        { id: 7, promptId: 8, version: 1, content: 'Gere casos de teste para a seguinte funcionalidade...', description: 'Versão inicial', isPublic: true, category: 'qa', createdBy: 5, createdAt: '2024-01-25' },
        
        // Versões de prompts pessoais (não públicas)
        { id: 8, promptId: 10, version: 1, content: 'Me ajude a entender este algoritmo...', description: 'Versão inicial', isPublic: false, category: '', createdBy: 2, createdAt: '2024-01-30' },
        { id: 9, promptId: 12, version: 1, content: 'Explique o padrão de projeto Strategy...', description: 'Versão inicial', isPublic: false, category: '', createdBy: 3, createdAt: '2024-02-15' }
    ]
};

// ============================================
// ESTADO DA APLICAÇÃO
// ============================================

let currentUser = null;
let currentTeamId = null;
let currentPromptId = null;

// ============================================
// FUNÇÕES DE AUTENTICAÇÃO
// ============================================

function quickLogin(username) {
    document.getElementById('loginUsername').value = username;
    document.getElementById('loginPassword').value = 'senha123';
    document.getElementById('loginForm').dispatchEvent(new Event('submit'));
}

document.getElementById('loginForm').addEventListener('submit', (e) => {
    e.preventDefault();
    
    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;
    
    // Simula autenticação
    const user = MOCK_DATA.users.find(u => u.username === username);
    
    if (user && password === 'senha123') {
        currentUser = user;
        showDashboard();
    } else {
        alert('Usuário ou senha inválidos!');
    }
});

document.getElementById('logoutBtn').addEventListener('click', () => {
    currentUser = null;
    showExplorePublic();
});

function showLogin() {
    document.getElementById('publicExploreScreen').classList.remove('active');
    document.getElementById('loginScreen').classList.add('active');
    document.getElementById('dashboardScreen').classList.remove('active');
    document.getElementById('logoutBtn').style.display = 'none';
    document.getElementById('exploreHeaderBtn').style.display = 'block';
    document.getElementById('username').textContent = 'Não logado';
}

function showExplorePublic() {
    document.getElementById('publicExploreScreen').classList.add('active');
    document.getElementById('loginScreen').classList.remove('active');
    document.getElementById('dashboardScreen').classList.remove('active');
    document.getElementById('logoutBtn').style.display = 'none';
    document.getElementById('exploreHeaderBtn').style.display = 'none';
    document.getElementById('username').textContent = 'Visitante';
    
    loadPublicPromptsPublic();
}

function showDashboard() {
    document.getElementById('publicExploreScreen').classList.remove('active');
    document.getElementById('loginScreen').classList.remove('active');
    document.getElementById('dashboardScreen').classList.add('active');
    document.getElementById('logoutBtn').style.display = 'block';
    document.getElementById('exploreHeaderBtn').style.display = 'block';
    document.getElementById('username').textContent = currentUser.username;
    
    configureUIByRole();
    loadDashboard();
}

function configureUIByRole() {
    const navigationTabs = document.getElementById('navigationTabs');
    
    if (currentUser.role === 'ADMIN') {
        // ADMIN: Foco em gestão do sistema
        navigationTabs.innerHTML = `
            <button class="tab active" data-tab="explore">🌐 Explorar</button>
            <button class="tab" data-tab="prompts">📝 Todos os Prompts</button>
            <button class="tab" data-tab="teams">👥 Gerenciar Times</button>
            <button class="tab" data-tab="users">👤 Gerenciar Usuários</button>
        `;
        
        // Ajusta títulos e botões para contexto admin
        document.querySelector('#promptsTab .section-header h2').textContent = 'Gerenciar Prompts';
        document.querySelector('#teamsTab .section-header h2').textContent = 'Gerenciar Times';
        document.querySelector('#exploreTab .section-header h2').textContent = 'Prompts Públicos';
        
        document.getElementById('createPromptBtn').textContent = '+ Novo Prompt';
        document.getElementById('createTeamBtn').style.display = 'block';
        document.getElementById('createUserBtn').style.display = 'block';
        
    } else {
        // USUÁRIO: Foco em trabalho com prompts e times
        const userTeams = getUserTeams();
        
        navigationTabs.innerHTML = `
            <button class="tab active" data-tab="prompts">📝 Meus Prompts</button>
            <button class="tab" data-tab="explore">🌐 Descobrir</button>
            ${userTeams.length > 0 ? '<button class="tab" data-tab="teams">👥 Meus Times</button>' : ''}
            <button class="tab" data-tab="users">👤 Perfil</button>
        `;
        
        // Ajusta títulos e botões para contexto usuário
        document.querySelector('#promptsTab .section-header h2').textContent = 'Meus Prompts';
        document.querySelector('#teamsTab .section-header h2').textContent = 'Meus Times';
        document.querySelector('#exploreTab .section-header h2').textContent = 'Descobrir Prompts';
        document.querySelector('#exploreTab .section-header div span').textContent = 'Encontre prompts compartilhados pela comunidade';
        
        document.getElementById('createPromptBtn').textContent = '+ Criar Prompt';
        document.getElementById('createTeamBtn').style.display = 'none';
        document.getElementById('createUserBtn').style.display = 'none';
        
        // Se não tem times, esconde botão de criar prompt de time
        if (userTeams.length === 0) {
            document.getElementById('createPromptBtn').style.display = 'none';
        }
    }
    
    // Reanexa event listeners nas tabs
    document.querySelectorAll('.tab').forEach(tab => {
        tab.addEventListener('click', () => {
            const tabName = tab.dataset.tab;
            
            // Atualiza tabs
            document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
            tab.classList.add('active');
            
            // Atualiza conteúdo
            document.querySelectorAll('.tab-content').forEach(content => content.classList.remove('active'));
            document.getElementById(tabName + 'Tab').classList.add('active');
            
            // Carrega dados
            if (tabName === 'prompts') loadPrompts();
            if (tabName === 'explore') loadPublicPrompts();
            if (tabName === 'teams') loadTeams();
            if (tabName === 'users') loadUsers();
        });
    });
}

// ============================================
// FUNÇÕES DE NAVEGAÇÃO
// ============================================

// Event listeners são configurados dinamicamente em configureUIByRole()

function loadDashboard() {
    loadPrompts();
    loadTeamsForFilters();
}

// ============================================
// FUNÇÕES DE PROMPTS
// ============================================

function loadPrompts() {
    const promptsList = document.getElementById('promptsList');
    
    let accessiblePrompts;
    if (currentUser.role === 'ADMIN') {
        // ADMIN vê todos os prompts do sistema
        accessiblePrompts = MOCK_DATA.prompts;
    } else {
        // Usuários veem apenas seus prompts
        accessiblePrompts = getAccessiblePrompts();
    }
    
    if (accessiblePrompts.length === 0) {
        const message = currentUser.role === 'ADMIN' 
            ? 'Nenhum prompt cadastrado no sistema'
            : 'Você ainda não criou nenhum prompt';
            
        promptsList.innerHTML = `
            <div class="empty-state">
                <div class="empty-state-icon">📝</div>
                <p>${message}</p>
                ${currentUser.role !== 'ADMIN' && getUserTeams().length === 0 ? '<p style="margin-top: 0.5rem; color: #999;">Você precisa fazer parte de um time para criar prompts</p>' : ''}
            </div>
        `;
        return;
    }
    
    promptsList.innerHTML = accessiblePrompts.map(prompt => createPromptCard(prompt)).join('');
}

function getAccessiblePrompts() {
    // Usuários só veem prompts pessoais e dos times que fazem parte
    const userTeamIds = MOCK_DATA.teamUsers
        .filter(tu => tu.userId === currentUser.id)
        .map(tu => tu.teamId);
    
    return MOCK_DATA.prompts.filter(p => 
        userTeamIds.includes(p.teamId) || p.ownerId === currentUser.id
    );
}

function createPromptCard(prompt) {
    const isTeamPrompt = prompt.teamId !== null;
    const team = isTeamPrompt ? MOCK_DATA.teams.find(t => t.id === prompt.teamId) : null;
    const owner = prompt.ownerId ? MOCK_DATA.users.find(u => u.id === prompt.ownerId) : null;
    
    const isAdmin = currentUser.role === 'ADMIN';
    const canEdit = isAdmin || canEditPrompt(prompt);
    const canDelete = isAdmin || canDeletePrompt(prompt);
    
    const versions = MOCK_DATA.promptVersions.filter(v => v.promptId === prompt.id);
    const publicVersionsCount = versions.filter(v => v.isPublic).length;
    
    return `
        <div class="card">
            <div class="card-header">
                <div>
                    <div class="card-title">${prompt.name}</div>
                    <div style="display: flex; gap: 0.5rem; margin-top: 0.5rem;">
                        <span class="card-badge ${isTeamPrompt ? 'badge-team' : 'badge-personal'}">
                            ${isTeamPrompt ? '👥 ' + team.name : '👤 ' + owner.username}
                        </span>
                    </div>
                </div>
            </div>
            <div class="card-description">${prompt.description || 'Sem descrição'}</div>
            <div class="card-info">
                📋 ${versions.length} versão(ões) ${publicVersionsCount > 0 ? `• 🌐 ${publicVersionsCount} pública(s)` : ''}
            </div>
            <div class="card-footer">
                <button class="btn btn-small btn-secondary" onclick="viewPromptVersions(${prompt.id})">📋 Versões</button>
                ${canEdit ? `<button class="btn btn-small btn-secondary" onclick="editPrompt(${prompt.id})">✏️ Editar</button>` : ''}
                ${canDelete ? `<button class="btn btn-small btn-danger" onclick="deletePrompt(${prompt.id})">🗑️ Deletar</button>` : ''}
            </div>
        </div>
    `;
}

function canEditPrompt(prompt) {
    // Prompts pessoais: só o dono pode editar
    if (prompt.ownerId === currentUser.id) return true;
    
    // Prompts de time: TEAM_OWNER e DEV podem editar
    if (prompt.teamId) {
        const membership = MOCK_DATA.teamUsers.find(
            tu => tu.teamId === prompt.teamId && tu.userId === currentUser.id
        );
        return membership && (membership.role === 'TEAM_OWNER' || membership.role === 'DEV');
    }
    
    return false;
}

function canDeletePrompt(prompt) {
    // Prompts pessoais: só o dono pode deletar
    if (prompt.ownerId === currentUser.id) return true;
    
    // Prompts de time: só TEAM_OWNER pode deletar
    if (prompt.teamId) {
        const membership = MOCK_DATA.teamUsers.find(
            tu => tu.teamId === prompt.teamId && tu.userId === currentUser.id
        );
        return membership && membership.role === 'TEAM_OWNER';
    }
    
    return false;
}

function filterPrompts() {
    const scope = document.getElementById('scopeFilter').value;
    const teamFilter = document.getElementById('teamFilter');
    const searchTerm = document.getElementById('searchPrompt').value.toLowerCase();
    
    // Mostra/esconde filtro de time
    if (scope === 'team') {
        teamFilter.style.display = 'block';
    } else {
        teamFilter.style.display = 'none';
    }
    
    let prompts = getAccessiblePrompts();
    
    // Filtro por escopo
    if (scope === 'my') {
        prompts = prompts.filter(p => p.ownerId === currentUser.id);
    } else if (scope === 'team' && teamFilter.value) {
        prompts = prompts.filter(p => p.teamId === parseInt(teamFilter.value));
    }
    
    // Filtro por nome
    if (searchTerm) {
        prompts = prompts.filter(p => 
            p.name.toLowerCase().includes(searchTerm) ||
            (p.description && p.description.toLowerCase().includes(searchTerm))
        );
    }
    
    const promptsList = document.getElementById('promptsList');
    if (prompts.length === 0) {
        promptsList.innerHTML = `
            <div class="empty-state">
                <div class="empty-state-icon">🔍</div>
                <p>Nenhum prompt encontrado com os filtros aplicados</p>
            </div>
        `;
    } else {
        promptsList.innerHTML = prompts.map(prompt => createPromptCard(prompt)).join('');
    }
}

function loadTeamsForFilters() {
    const teamFilter = document.getElementById('teamFilter');
    const userTeams = getUserTeams();
    
    const options = userTeams.map(team => 
        `<option value="${team.id}">${team.name}</option>`
    ).join('');
    
    teamFilter.innerHTML = '<option value="">Selecione um time</option>' + options;
}

function getUserTeams() {
    // Retorna apenas os times que o usuário faz parte
    const userTeamIds = MOCK_DATA.teamUsers
        .filter(tu => tu.userId === currentUser.id)
        .map(tu => tu.teamId);
    
    return MOCK_DATA.teams.filter(t => userTeamIds.includes(t.id));
}

// ============================================
// FUNÇÕES DE TIMES
// ============================================

function loadTeams() {
    const teamsList = document.getElementById('teamsList');
    
    let teams;
    if (currentUser.role === 'ADMIN') {
        // ADMIN vê todos os times
        teams = MOCK_DATA.teams;
    } else {
        // Usuários veem apenas seus times
        teams = getUserTeams();
    }
    
    if (teams.length === 0) {
        const message = currentUser.role === 'ADMIN'
            ? 'Nenhum time cadastrado no sistema'
            : 'Você não faz parte de nenhum time ainda';
            
        teamsList.innerHTML = `
            <div class="empty-state">
                <div class="empty-state-icon">👥</div>
                <p>${message}</p>
            </div>
        `;
        return;
    }
    
    teamsList.innerHTML = teams.map(team => createTeamCard(team)).join('');
}

function createTeamCard(team) {
    const membership = MOCK_DATA.teamUsers.find(
        tu => tu.teamId === team.id && tu.userId === currentUser.id
    );
    
    const memberCount = MOCK_DATA.teamUsers.filter(tu => tu.teamId === team.id).length;
    const promptCount = MOCK_DATA.prompts.filter(p => p.teamId === team.id).length;
    
    const isAdmin = currentUser.role === 'ADMIN';
    const canManage = isAdmin || canManageTeam(team.id);
    const canViewMembers = isAdmin || canViewTeamMembers(team.id);
    
    return `
        <div class="card">
            <div class="card-header">
                <div>
                    <div class="card-title">${team.name}</div>
                    ${membership ? `<span class="card-badge badge-${membership.role.toLowerCase().replace('_', '-')}">${getRoleLabel(membership.role)}</span>` : ''}
                    ${isAdmin && !membership ? '<span class="card-badge badge-owner">ADMIN</span>' : ''}
                </div>
            </div>
            <div class="card-description">${team.description}</div>
            <div class="card-info">
                👥 ${memberCount} membros • 📝 ${promptCount} prompts
            </div>
            <div class="card-footer">
                ${canViewMembers ? `<button class="btn btn-small btn-secondary" onclick="viewTeamMembers(${team.id})">👥 Membros</button>` : ''}
                ${canManage ? `<button class="btn btn-small btn-secondary" onclick="editTeam(${team.id})">✏️ Editar</button>` : ''}
                ${isAdmin ? `<button class="btn btn-small btn-danger" onclick="deleteTeam(${team.id})">🗑️ Deletar</button>` : ''}
            </div>
        </div>
    `;
}

function getRoleLabel(role) {
    const labels = {
        'TEAM_OWNER': 'Owner',
        'DEV': 'Dev',
        'VIEWER': 'Viewer'
    };
    return labels[role] || role;
}

function canManageTeam(teamId) {
    // Só TEAM_OWNER pode gerenciar o time
    const membership = MOCK_DATA.teamUsers.find(
        tu => tu.teamId === teamId && tu.userId === currentUser.id
    );
    
    return membership && membership.role === 'TEAM_OWNER';
}

function canViewTeamMembers(teamId) {
    // TEAM_OWNER e DEV podem ver membros
    const membership = MOCK_DATA.teamUsers.find(
        tu => tu.teamId === teamId && tu.userId === currentUser.id
    );
    
    return membership && (membership.role === 'TEAM_OWNER' || membership.role === 'DEV');
}

// ============================================
// FUNÇÕES DE USUÁRIOS
// ============================================

function loadUsers() {
    const usersList = document.getElementById('usersList');
    const usersTabTitle = document.getElementById('usersTabTitle');
    
    if (currentUser.role === 'ADMIN') {
        // ADMIN vê todos os usuários
        usersTabTitle.textContent = 'Usuários';
        usersList.innerHTML = `
            <table class="table">
                <thead>
                    <tr>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Times</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    ${MOCK_DATA.users.map(user => createUserRow(user)).join('')}
                </tbody>
            </table>
        `;
    } else {
        // Usuários comuns veem apenas seu próprio perfil
        usersTabTitle.textContent = 'Meu Perfil';
        usersList.innerHTML = `
            <div class="card" style="max-width: 600px; margin: 0 auto;">
                <div class="card-header">
                    <div class="card-title">Informações do Perfil</div>
                </div>
                <div style="padding: 1rem 0;">
                    <div style="margin-bottom: 1rem;">
                        <strong>Username:</strong> ${currentUser.username}
                    </div>
                    <div style="margin-bottom: 1rem;">
                        <strong>Email:</strong> ${currentUser.email}
                    </div>
                    <div style="margin-bottom: 1rem;">
                        <strong>Role:</strong> <span class="card-badge badge-dev">${currentUser.role}</span>
                    </div>
                    <div style="margin-bottom: 1rem;">
                        <strong>Times:</strong> ${getUserTeams().map(t => t.name).join(', ') || 'Nenhum'}
                    </div>
                </div>
                <div class="card-footer">
                    <button class="btn btn-small btn-secondary" onclick="editUser(${currentUser.id})">✏️ Editar Perfil</button>
                </div>
            </div>
        `;
    }
}

function createUserRow(user) {
    const userTeams = MOCK_DATA.teamUsers
        .filter(tu => tu.userId === user.id)
        .map(tu => MOCK_DATA.teams.find(t => t.id === tu.teamId).name)
        .join(', ');
    
    return `
        <tr>
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td><span class="card-badge ${user.role === 'ADMIN' ? 'badge-owner' : 'badge-dev'}">${user.role}</span></td>
            <td>${userTeams || '-'}</td>
            <td>
                <button class="btn btn-small btn-secondary" onclick="editUser(${user.id})">✏️</button>
            </td>
        </tr>
    `;
}

// ============================================
// MODALS
// ============================================

function showCreatePromptModal() {
    document.getElementById('promptModalTitle').textContent = 'Novo Prompt';
    document.getElementById('promptForm').reset();
    
    // Carrega apenas times onde o usuário pode criar prompts (TEAM_OWNER ou DEV)
    const promptTeamId = document.getElementById('promptTeamId');
    const teamsWhereCanCreate = getUserTeams().filter(team => canCreateTeamPrompt(team.id));
    
    if (teamsWhereCanCreate.length === 0) {
        // Se não pode criar em nenhum time, força tipo pessoal
        document.getElementById('promptType').innerHTML = '<option value="personal">Prompt Pessoal</option>';
    } else {
        // Mostra ambas opções
        document.getElementById('promptType').innerHTML = `
            <option value="team">Prompt de Time</option>
            <option value="personal">Prompt Pessoal</option>
        `;
        
        promptTeamId.innerHTML = '<option value="">Selecione um time</option>' +
            teamsWhereCanCreate.map(team => `<option value="${team.id}">${team.name}</option>`).join('');
    }
    
    togglePromptType();
    openModal('promptModal');
}

function togglePromptType() {
    const type = document.getElementById('promptType').value;
    const teamGroup = document.getElementById('teamSelectGroup');
    const teamSelect = document.getElementById('promptTeamId');
    
    if (type === 'team') {
        teamGroup.style.display = 'block';
        teamSelect.required = true;
    } else {
        teamGroup.style.display = 'none';
        teamSelect.required = false;
    }
}

function canCreateTeamPrompt(teamId) {
    // Só TEAM_OWNER e DEV podem criar prompts para o time
    const membership = MOCK_DATA.teamUsers.find(
        tu => tu.teamId === teamId && tu.userId === currentUser.id
    );
    
    return membership && (membership.role === 'TEAM_OWNER' || membership.role === 'DEV');
}

document.getElementById('promptForm').addEventListener('submit', (e) => {
    e.preventDefault();
    
    const type = document.getElementById('promptType').value;
    const name = document.getElementById('promptName').value;
    const description = document.getElementById('promptDescription').value;
    
    // Validação: se for prompt de time, verifica se pode criar
    if (type === 'team') {
        const teamId = parseInt(document.getElementById('promptTeamId').value);
        if (!canCreateTeamPrompt(teamId)) {
            alert('❌ Você não tem permissão para criar prompts neste time. Apenas TEAM_OWNER e DEV podem criar.');
            return;
        }
    }
    
    const newPrompt = {
        id: MOCK_DATA.prompts.length + 1,
        name,
        description,
        teamId: type === 'team' ? parseInt(document.getElementById('promptTeamId').value) : null,
        ownerId: type === 'personal' ? currentUser.id : null
    };
    
    MOCK_DATA.prompts.push(newPrompt);
    
    closeModal('promptModal');
    loadPrompts();
    
    alert('✅ Prompt criado com sucesso!');
});

function showCreateTeamModal() {
    if (currentUser.role !== 'ADMIN') {
        alert('Apenas administradores podem criar times');
        return;
    }
    openModal('teamModal');
}

document.getElementById('teamForm').addEventListener('submit', (e) => {
    e.preventDefault();
    
    if (currentUser.role !== 'ADMIN') {
        alert('❌ Apenas administradores podem criar times');
        return;
    }
    
    const name = document.getElementById('teamName').value;
    const description = document.getElementById('teamDescription').value;
    
    const newTeam = {
        id: MOCK_DATA.teams.length + 1,
        name,
        description,
        status: 'ACTIVE'
    };
    
    MOCK_DATA.teams.push(newTeam);
    
    // Adiciona usuário atual como TEAM_OWNER
    MOCK_DATA.teamUsers.push({
        teamId: newTeam.id,
        userId: currentUser.id,
        role: 'TEAM_OWNER'
    });
    
    closeModal('teamModal');
    loadTeams();
    loadTeamsForFilters();
    
    alert('✅ Time criado com sucesso!');
});

function viewTeamMembers(teamId) {
    currentTeamId = teamId;
    const team = MOCK_DATA.teams.find(t => t.id === teamId);
    
    document.getElementById('membersModalTitle').textContent = `Membros do ${team.name}`;
    
    loadTeamMembers();
    openModal('membersModal');
}

function loadTeamMembers() {
    const tbody = document.getElementById('membersTableBody');
    const members = MOCK_DATA.teamUsers
        .filter(tu => tu.teamId === currentTeamId)
        .map(tu => {
            const user = MOCK_DATA.users.find(u => u.id === tu.userId);
            return { ...tu, user };
        });
    
    tbody.innerHTML = members.map(member => `
        <tr>
            <td>${member.user.username}</td>
            <td>${member.user.email}</td>
            <td><span class="card-badge badge-${member.role.toLowerCase()}">${getRoleLabel(member.role)}</span></td>
            <td>
                ${canManageTeam(currentTeamId) ? `
                    <button class="btn btn-small btn-danger" onclick="removeMember(${member.userId})">Remover</button>
                ` : '-'}
            </td>
        </tr>
    `).join('');
    
    // Carrega usuários disponíveis para adicionar
    const availableUsers = MOCK_DATA.users.filter(u => 
        !members.some(m => m.userId === u.id)
    );
    
    document.getElementById('newMemberUserId').innerHTML = 
        '<option value="">Selecione um usuário</option>' +
        availableUsers.map(u => `<option value="${u.id}">${u.username}</option>`).join('');
}

function showAddMemberForm() {
    const form = document.getElementById('addMemberForm');
    form.style.display = form.style.display === 'none' ? 'block' : 'none';
}

document.getElementById('addMemberFormElement').addEventListener('submit', (e) => {
    e.preventDefault();
    
    const userId = parseInt(document.getElementById('newMemberUserId').value);
    const role = document.getElementById('newMemberRole').value;
    
    MOCK_DATA.teamUsers.push({
        teamId: currentTeamId,
        userId,
        role
    });
    
    loadTeamMembers();
    document.getElementById('addMemberFormElement').reset();
    document.getElementById('addMemberForm').style.display = 'none';
    
    alert('✅ Membro adicionado com sucesso!');
});

function removeMember(userId) {
    if (!confirm('Tem certeza que deseja remover este membro?')) return;
    
    const index = MOCK_DATA.teamUsers.findIndex(
        tu => tu.teamId === currentTeamId && tu.userId === userId
    );
    
    if (index !== -1) {
        MOCK_DATA.teamUsers.splice(index, 1);
        loadTeamMembers();
        alert('✅ Membro removido com sucesso!');
    }
}

function openModal(modalId) {
    document.getElementById(modalId).classList.add('active');
}

function closeModal(modalId) {
    document.getElementById(modalId).classList.remove('active');
}

// Funções placeholder
function editPrompt(id) {
    alert(`Editar prompt ${id} - Funcionalidade a ser implementada`);
}

// Funções placeholder
function editPrompt(id) {
    alert(`Editar prompt ${id} - Funcionalidade a ser implementada`);
}

function deletePrompt(id) {
    if (!confirm('Tem certeza que deseja deletar este prompt?')) return;
    
    const index = MOCK_DATA.prompts.findIndex(p => p.id === id);
    if (index !== -1) {
        MOCK_DATA.prompts.splice(index, 1);
        loadPrompts();
        alert('✅ Prompt deletado com sucesso!');
    }
}

// ============================================
// FUNÇÕES DE PROMPT VERSIONS
// ============================================

function viewPromptVersions(promptId) {
    currentPromptId = promptId;
    const prompt = MOCK_DATA.prompts.find(p => p.id === promptId);
    
    document.getElementById('versionsModalTitle').textContent = `Versões: ${prompt.name}`;
    
    // Verifica se pode criar versões
    const canCreate = canEditPrompt(prompt);
    document.getElementById('createVersionBtn').style.display = canCreate ? 'block' : 'none';
    
    loadPromptVersions();
    openModal('versionsModal');
}

function loadPromptVersions() {
    const versionsList = document.getElementById('versionsList');
    const versions = MOCK_DATA.promptVersions
        .filter(v => v.promptId === currentPromptId)
        .sort((a, b) => b.version - a.version); // Mais recente primeiro
    
    if (versions.length === 0) {
        versionsList.innerHTML = `
            <div class="empty-state">
                <div class="empty-state-icon">📋</div>
                <p>Nenhuma versão criada ainda</p>
            </div>
        `;
        return;
    }
    
    versionsList.innerHTML = versions.map(version => createVersionCard(version)).join('');
}

function createVersionCard(version) {
    const creator = MOCK_DATA.users.find(u => u.id === version.createdBy);
    const prompt = MOCK_DATA.prompts.find(p => p.id === version.promptId);
    const canEdit = canEditPrompt(prompt);
    
    return `
        <div class="version-card">
            <div class="version-header">
                <div>
                    <span class="version-number">v${version.version}</span>
                    ${version.isPublic ? '<span class="card-badge badge-public">🌐 Público</span>' : '<span class="card-badge badge-private">🔒 Privado</span>'}
                    ${version.category ? `<span class="card-badge badge-category">${version.category}</span>` : ''}
                </div>
                <span class="version-date">${formatDate(version.createdAt)}</span>
            </div>
            <div class="version-description">${version.description || 'Sem descrição'}</div>
            <div class="version-content">
                <strong>Conteúdo:</strong>
                <pre>${version.content}</pre>
            </div>
            <div class="version-footer">
                <span style="font-size: 0.85rem; color: #999;">Por ${creator.username}</span>
                ${canEdit ? `<button class="btn btn-small btn-danger" onclick="deleteVersion(${version.id})">🗑️ Deletar</button>` : ''}
            </div>
        </div>
    `;
}

function showCreateVersionForm() {
    document.getElementById('createVersionForm').style.display = 'block';
}

function hideCreateVersionForm() {
    document.getElementById('createVersionForm').style.display = 'none';
    document.getElementById('versionForm').reset();
}

document.getElementById('versionForm').addEventListener('submit', (e) => {
    e.preventDefault();
    
    const content = document.getElementById('versionContent').value;
    const description = document.getElementById('versionDescription').value;
    const isPublic = document.getElementById('versionIsPublic').checked;
    const category = document.getElementById('versionCategory').value;
    
    const existingVersions = MOCK_DATA.promptVersions.filter(v => v.promptId === currentPromptId);
    const nextVersion = existingVersions.length > 0 ? Math.max(...existingVersions.map(v => v.version)) + 1 : 1;
    
    const newVersion = {
        id: MOCK_DATA.promptVersions.length + 1,
        promptId: currentPromptId,
        version: nextVersion,
        content,
        description,
        isPublic,
        category: isPublic ? category : '',
        createdBy: currentUser.id,
        createdAt: new Date().toISOString().split('T')[0]
    };
    
    MOCK_DATA.promptVersions.push(newVersion);
    
    hideCreateVersionForm();
    loadPromptVersions();
    
    alert('✅ Nova versão criada com sucesso!');
});

function deleteVersion(versionId) {
    if (!confirm('Tem certeza que deseja deletar esta versão?')) return;
    
    const index = MOCK_DATA.promptVersions.findIndex(v => v.id === versionId);
    if (index !== -1) {
        MOCK_DATA.promptVersions.splice(index, 1);
        loadPromptVersions();
        alert('✅ Versão deletada com sucesso!');
    }
}

// ============================================
// FUNÇÕES DE EXPLORAR PROMPTS PÚBLICOS
// ============================================

function loadPublicPrompts() {
    const publicPromptsList = document.getElementById('publicPromptsList');
    const publicVersions = getPublicPromptVersions();
    
    if (publicVersions.length === 0) {
        publicPromptsList.innerHTML = `
            <div class="empty-state">
                <div class="empty-state-icon">🌐</div>
                <p>Nenhum prompt público disponível no momento</p>
            </div>
        `;
        return;
    }
    
    publicPromptsList.innerHTML = publicVersions.map(item => createPublicPromptCard(item)).join('');
}

function getPublicPromptVersions() {
    // Retorna apenas versões públicas com informações do prompt
    return MOCK_DATA.promptVersions
        .filter(v => v.isPublic)
        .map(version => {
            const prompt = MOCK_DATA.prompts.find(p => p.id === version.promptId);
            const creator = MOCK_DATA.users.find(u => u.id === version.createdBy);
            return { ...version, prompt, creator };
        });
}

function createPublicPromptCard(item) {
    const { prompt, creator, version, category, description, createdAt } = item;
    
    return `
        <div class="card">
            <div class="card-header">
                <div>
                    <div class="card-title">${prompt.name}</div>
                    <div style="display: flex; gap: 0.5rem; margin-top: 0.5rem;">
                        ${category ? `<span class="card-badge badge-category">${category}</span>` : ''}
                        <span class="card-badge badge-version">v${version}</span>
                    </div>
                </div>
            </div>
            <div class="card-description">${description || prompt.description || 'Sem descrição'}</div>
            <div class="card-info">
                👤 ${creator.username} • 📅 ${formatDate(createdAt)}
            </div>
            <div class="card-footer">
                <button class="btn btn-small btn-primary" onclick="viewPublicPromptVersion(${item.id})">👁️ Ver Detalhes</button>
            </div>
        </div>
    `;
}

function viewPublicPromptVersion(versionId) {
    const version = MOCK_DATA.promptVersions.find(v => v.id === versionId);
    const prompt = MOCK_DATA.prompts.find(p => p.id === version.promptId);
    const creator = MOCK_DATA.users.find(u => u.id === version.createdBy);
    
    document.getElementById('publicVersionModalTitle').textContent = prompt.name;
    
    document.getElementById('publicVersionDetails').innerHTML = `
        <div style="margin-bottom: 1.5rem;">
            <div style="display: flex; gap: 0.5rem; margin-bottom: 1rem;">
                <span class="card-badge badge-version">v${version.version}</span>
                ${version.category ? `<span class="card-badge badge-category">${version.category}</span>` : ''}
                <span class="card-badge badge-public">🌐 Público</span>
            </div>
            
            <div style="margin-bottom: 1rem;">
                <strong>Descrição:</strong>
                <p style="margin-top: 0.5rem; color: #666;">${version.description || prompt.description || 'Sem descrição'}</p>
            </div>
            
            <div style="margin-bottom: 1rem;">
                <strong>Conteúdo do Prompt:</strong>
                <pre style="background: #f8f9fa; padding: 1rem; border-radius: 8px; margin-top: 0.5rem; white-space: pre-wrap; word-wrap: break-word;">${version.content}</pre>
            </div>
            
            <div style="display: flex; justify-content: space-between; align-items: center; padding-top: 1rem; border-top: 1px solid #e0e0e0;">
                <span style="font-size: 0.9rem; color: #999;">
                    Criado por <strong>${creator.username}</strong> em ${formatDate(version.createdAt)}
                </span>
                <button class="btn btn-small btn-primary" onclick="copyPromptContent(${version.id})">📋 Copiar Conteúdo</button>
            </div>
        </div>
    `;
    
    openModal('publicVersionModal');
}

function copyPromptContent(versionId) {
    const version = MOCK_DATA.promptVersions.find(v => v.id === versionId);
    
    navigator.clipboard.writeText(version.content).then(() => {
        alert('✅ Conteúdo copiado para a área de transferência!');
    }).catch(() => {
        alert('❌ Erro ao copiar conteúdo');
    });
}

function filterPublicPrompts() {
    const searchTerm = document.getElementById('searchPublicPrompt').value.toLowerCase();
    const category = document.getElementById('categoryFilter').value;
    
    let publicVersions = getPublicPromptVersions();
    
    // Filtro por categoria
    if (category) {
        publicVersions = publicVersions.filter(item => item.category === category);
    }
    
    // Filtro por busca
    if (searchTerm) {
        publicVersions = publicVersions.filter(item => 
            item.prompt.name.toLowerCase().includes(searchTerm) ||
            (item.description && item.description.toLowerCase().includes(searchTerm)) ||
            (item.prompt.description && item.prompt.description.toLowerCase().includes(searchTerm))
        );
    }
    
    const publicPromptsList = document.getElementById('publicPromptsList');
    if (publicVersions.length === 0) {
        publicPromptsList.innerHTML = `
            <div class="empty-state">
                <div class="empty-state-icon">🔍</div>
                <p>Nenhum prompt encontrado com os filtros aplicados</p>
            </div>
        `;
    } else {
        publicPromptsList.innerHTML = publicVersions.map(item => createPublicPromptCard(item)).join('');
    }
}

function filterPublicPromptsPublic() {
    const searchTerm = document.getElementById('searchPublicPromptPublic').value.toLowerCase();
    const category = document.getElementById('categoryFilterPublic').value;
    
    let publicVersions = getPublicPromptVersions();
    
    // Filtro por categoria
    if (category) {
        publicVersions = publicVersions.filter(item => item.category === category);
    }
    
    // Filtro por busca
    if (searchTerm) {
        publicVersions = publicVersions.filter(item => 
            item.prompt.name.toLowerCase().includes(searchTerm) ||
            (item.description && item.description.toLowerCase().includes(searchTerm)) ||
            (item.prompt.description && item.prompt.description.toLowerCase().includes(searchTerm))
        );
    }
    
    const publicPromptsList = document.getElementById('publicPromptsListPublic');
    if (publicVersions.length === 0) {
        publicPromptsList.innerHTML = `
            <div class="empty-state">
                <div class="empty-state-icon">🔍</div>
                <p>Nenhum prompt encontrado com os filtros aplicados</p>
            </div>
        `;
    } else {
        publicPromptsList.innerHTML = publicVersions.map(item => createPublicPromptCardPublic(item)).join('');
    }
}

function loadPublicPromptsPublic() {
    const publicPromptsList = document.getElementById('publicPromptsListPublic');
    const publicVersions = getPublicPromptVersions();
    
    if (publicVersions.length === 0) {
        publicPromptsList.innerHTML = `
            <div class="empty-state">
                <div class="empty-state-icon">🌐</div>
                <p>Nenhum prompt público disponível no momento</p>
            </div>
        `;
        return;
    }
    
    publicPromptsList.innerHTML = publicVersions.map(item => createPublicPromptCardPublic(item)).join('');
}

function createPublicPromptCardPublic(item) {
    const { prompt, creator, version, category, description, createdAt } = item;
    
    return `
        <div class="card">
            <div class="card-header">
                <div>
                    <div class="card-title">${prompt.name}</div>
                    <div style="display: flex; gap: 0.5rem; margin-top: 0.5rem;">
                        ${category ? `<span class="card-badge badge-category">${category}</span>` : ''}
                        <span class="card-badge badge-version">v${version}</span>
                    </div>
                </div>
            </div>
            <div class="card-description">${description || prompt.description || 'Sem descrição'}</div>
            <div class="card-info">
                👤 ${creator.username} • 📅 ${formatDate(createdAt)}
            </div>
            <div class="card-footer">
                <button class="btn btn-small btn-primary" onclick="viewPublicPromptVersionPublic(${item.id})">👁️ Ver Detalhes</button>
            </div>
        </div>
    `;
}

function viewPublicPromptVersionPublic(versionId) {
    const version = MOCK_DATA.promptVersions.find(v => v.id === versionId);
    const prompt = MOCK_DATA.prompts.find(p => p.id === version.promptId);
    const creator = MOCK_DATA.users.find(u => u.id === version.createdBy);
    
    document.getElementById('publicVersionModalTitle').textContent = prompt.name;
    
    document.getElementById('publicVersionDetails').innerHTML = `
        <div style="margin-bottom: 1.5rem;">
            <div style="display: flex; gap: 0.5rem; margin-bottom: 1rem;">
                <span class="card-badge badge-version">v${version.version}</span>
                ${version.category ? `<span class="card-badge badge-category">${version.category}</span>` : ''}
                <span class="card-badge badge-public">🌐 Público</span>
            </div>
            
            <div style="margin-bottom: 1rem;">
                <strong>Descrição:</strong>
                <p style="margin-top: 0.5rem; color: #666;">${version.description || prompt.description || 'Sem descrição'}</p>
            </div>
            
            <div style="margin-bottom: 1rem;">
                <strong>Conteúdo do Prompt:</strong>
                <pre style="background: #f8f9fa; padding: 1rem; border-radius: 8px; margin-top: 0.5rem; white-space: pre-wrap; word-wrap: break-word;">${version.content}</pre>
            </div>
            
            <div style="display: flex; justify-content: space-between; align-items: center; padding-top: 1rem; border-top: 1px solid #e0e0e0;">
                <span style="font-size: 0.9rem; color: #999;">
                    Criado por <strong>${creator.username}</strong> em ${formatDate(version.createdAt)}
                </span>
                <button class="btn btn-small btn-primary" onclick="copyPromptContent(${version.id})">📋 Copiar Conteúdo</button>
            </div>
            
            ${!currentUser ? `
                <div style="margin-top: 1.5rem; padding: 1rem; background: #f0f7ff; border-radius: 8px; text-align: center;">
                    <p style="margin-bottom: 0.5rem; color: #1e40af;">Gostou deste prompt?</p>
                    <button class="btn btn-primary btn-small" onclick="closeModal('publicVersionModal'); showLogin();">Entrar para criar seus próprios prompts</button>
                </div>
            ` : ''}
        </div>
    `;
    
    openModal('publicVersionModal');
}

// ============================================
// FUNÇÕES UTILITÁRIAS
// ============================================

function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('pt-BR');
}

function editTeam(id) {
    alert(`Editar time ${id} - Funcionalidade a ser implementada`);
}

function deleteTeam(teamId) {
    if (!confirm('Tem certeza que deseja deletar este time? Todos os prompts do time também serão deletados.')) return;
    
    // Remove time
    const teamIndex = MOCK_DATA.teams.findIndex(t => t.id === teamId);
    if (teamIndex !== -1) {
        MOCK_DATA.teams.splice(teamIndex, 1);
    }
    
    // Remove membros do time
    MOCK_DATA.teamUsers = MOCK_DATA.teamUsers.filter(tu => tu.teamId !== teamId);
    
    // Remove prompts do time
    MOCK_DATA.prompts = MOCK_DATA.prompts.filter(p => p.teamId !== teamId);
    
    loadTeams();
    alert('✅ Time deletado com sucesso!');
}

function showCreateUserModal() {
    alert('Criar usuário - Funcionalidade a ser implementada');
}

function editUser(id) {
    alert(`Editar usuário ${id} - Funcionalidade a ser implementada`);
}

// Fecha modal ao clicar fora
window.addEventListener('click', (e) => {
    if (e.target.classList.contains('modal')) {
        e.target.classList.remove('active');
    }
});

// Inicializa a aplicação mostrando a tela de explorar público
window.addEventListener('DOMContentLoaded', () => {
    showExplorePublic();
});
