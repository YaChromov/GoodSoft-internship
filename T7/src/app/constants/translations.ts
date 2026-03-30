export interface TranslationKeys {
  authenticatedAs: string;
  logout: string;
  home: string;
  users: string;
  addUser: string;
  loginTitle: string;
  loginLabel: string;
  passwordLabel: string;
  loginBtn: string;
  loginError: string;
  passwordEditTitle: string;
  oldPasswordLabel: string;
  newPasswordLabel: string;
  submitBtn: string;
  cancelBtn: string;
  passwordError: string;
  userAddTitle: string;
  userEditTitle: string;
  saveError: string;
  rolesLabel: string;
  surnameLabel: string;
  nameLabel: string;
  patronymicLabel: string;
  emailLabel: string;
  birthdayLabel: string;
  saveBtn: string;
  registerBtn: string;
  roleRequiredError: string;
  loadUserError: string;
  urlParamError: string;
  loginCol: string;
  fioCol: string;
  emailCol: string;
  birthdayCol: string;
  rolesCol: string;
  actionsCol: string;
  editBtn: string;
  deleteBtn: string;
  deleteConfirm: string;
  selfDeleteError: string;
  deleteServerError: string;
  loadUsersError: string;
}

export const TRANSLATIONS: Record<'ru' | 'en', TranslationKeys> = {
  ru: {
    authenticatedAs: 'Вы вошли как:',
    logout: 'Выйти',
    home: 'Главная',
    users: 'Пользователи',
    addUser: '+ Добавить пользователя',
    loginTitle: 'Вход в систему',
    loginLabel: 'ЛОГИН',
    passwordLabel: 'ПАРОЛЬ',
    loginBtn: 'Войти',
    loginError: 'Неверный логин или пароль',
    passwordEditTitle: 'ИЗМЕНЕНИЕ ПАРОЛЯ',
    oldPasswordLabel: 'СТАРЫЙ ПАРОЛЬ',
    newPasswordLabel: 'НОВЫЙ ПАРОЛЬ',
    submitBtn: 'ОТПРАВИТЬ',
    cancelBtn: 'ОТМЕНА',
    passwordError: 'Ошибка при смене пароля',
    userAddTitle: 'РЕГИСТРАЦИЯ ПОЛЬЗОВАТЕЛЯ',
    userEditTitle: 'РЕДАКТИРОВАНИЕ ПОЛЬЗОВАТЕЛЯ',
    saveError: 'Ошибка сохранения!',
    rolesLabel: 'РОЛИ',
    surnameLabel: 'ФАМИЛИЯ',
    nameLabel: 'ИМЯ',
    patronymicLabel: 'ОТЧЕСТВО',
    emailLabel: 'EMAIL',
    birthdayLabel: 'ДАТА РОЖДЕНИЯ',
    saveBtn: 'СОХРАНИТЬ',
    registerBtn: 'ЗАРЕГИСТРИРОВАТЬ',
    roleRequiredError: 'Выберите хотя бы одну роль',
    loadUserError: 'Не удалось загрузить данные пользователя',
    urlParamError: 'Логин пользователя не указан в URL',
    loginCol: 'ЛОГИН',
    fioCol: 'ФИО',
    emailCol: 'EMAIL',
    birthdayCol: 'ДАТА РОЖД.',
    rolesCol: 'РОЛИ',
    actionsCol: 'ДЕЙСТВИЯ',
    editBtn: 'ИЗМЕНИТЬ',
    deleteBtn: 'УДАЛИТЬ',
    deleteConfirm: 'Вы уверены, что хотите удалить',
    selfDeleteError: 'Вы не можете удалить самого себя!',
    deleteServerError: 'Сервер отклонил запрос на удаление',
    loadUsersError: 'Не удалось получить данные с сервера'
  },
  en: {
    authenticatedAs: 'Authenticated as:',
    logout: 'Logout',
    home: 'Home',
    users: 'Users',
    addUser: '+ Add user',
    loginTitle: 'System Login',
    loginLabel: 'LOGIN',
    passwordLabel: 'PASSWORD',
    loginBtn: 'Login',
    loginError: 'Invalid login or password',
    passwordEditTitle: 'CHANGE PASSWORD',
    oldPasswordLabel: 'OLD PASSWORD',
    newPasswordLabel: 'NEW PASSWORD',
    submitBtn: 'SUBMIT',
    cancelBtn: 'CANCEL',
    passwordError: 'Error changing password',
    userAddTitle: 'USER REGISTRATION',
    userEditTitle: 'EDIT USER',
    saveError: 'Save error!',
    rolesLabel: 'ROLES',
    surnameLabel: 'SURNAME',
    nameLabel: 'NAME',
    patronymicLabel: 'PATRONYMIC',
    emailLabel: 'EMAIL',
    birthdayLabel: 'BIRTH DAY',
    saveBtn: 'SAVE',
    registerBtn: 'REGISTER',
    roleRequiredError: 'Select at least one role',
    loadUserError: 'Failed to load user data',
    urlParamError: 'User login not specified in URL',
    loginCol: 'LOGIN',
    fioCol: 'FULL NAME',
    emailCol: 'EMAIL',
    birthdayCol: 'BIRTH DATE',
    rolesCol: 'ROLES',
    actionsCol: 'ACTIONS',
    editBtn: 'EDIT',
    deleteBtn: 'DELETE',
    deleteConfirm: 'Are you sure you want to delete',
    selfDeleteError: 'You cannot delete yourself!',
    deleteServerError: 'Server rejected deletion request',
    loadUsersError: 'Failed to retrieve data from server'
  }
};
