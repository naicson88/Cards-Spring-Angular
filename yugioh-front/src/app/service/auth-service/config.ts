 interface Config {
    [key: string]: string;
    auth: 'session' | 'token';
}

export const configg: Config = {
    apiUrl: '/api',
    adminUrl: '/api/admin',
    authUrl: 'http://localhost:8080/yugiohAPI',
    auth: 'token'
}