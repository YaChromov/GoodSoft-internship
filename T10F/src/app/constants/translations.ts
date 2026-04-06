export interface TranslationKeys {
  authenticatedAs: string;
  logout: string;
  home: string;
  users: string;
  orders: string;
  addUser: string;
  loginTitle: string;
  loginLabel: string;
  passwordLabel: string;
  loginBtn: string;
  loginError: string;
  registrationTitle: string;
  noAccountText: string;
  passwordEditTitle: string;
  oldPasswordLabel: string;
  newPasswordLabel: string;
  submitBtn: string;
  cancelBtn: string;
  passwordError: string;
  userAddTitle: string;
  userEditTitle: string;
  addOrderTitle: string;
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
  actionsCol: string;
  editBtn: string;
  deleteBtn: string;
  deleteServerError: string;
  yes: string;
  no: string;
  loginCol: string;
  fioCol: string;
  emailCol: string;
  birthdayCol: string;
  rolesCol: string;
  deleteConfirm: string;
  selfDeleteError: string;
  loadUsersError: string;
  clientCol: string;
  adminCol: string;
  capacityCol: string;
  classCol: string;
  daysCol: string;
  statusCol: string;
  paidCol: string;
  loadOrdersError: string;
  deleteOrderConfirm: string;
  processBtn: string;
  statusPending: string;
  statusConfirmed: string;
  statusRejected: string;
  statusPaid: string;
  pendingOrders: string;
  confirmStatus: string;
  rejectStatus: string;
}

export const TRANSLATIONS: Record<'ru' | 'en', TranslationKeys> = {
  ru: {
    authenticatedAs: 'Вы вошли как:',
    logout: 'Выйти',
    home: 'Главная',
    users: 'Пользователи',
    orders: 'Заказы',
    addUser: '+ Добавить пользователя',
    loginTitle: 'Вход в систему',
    loginLabel: 'ЛОГИН',
    passwordLabel: 'ПАРОЛЬ',
    loginBtn: 'Войти',
    loginError: 'Неверный логин или пароль',
    registrationTitle: 'Регистрация',
    noAccountText: 'Нет аккаунта?',
    passwordEditTitle: 'ИЗМЕНЕНИЕ ПАРОЛЯ',
    oldPasswordLabel: 'СТАРЫЙ ПАРОЛЬ',
    newPasswordLabel: 'НОВЫЙ ПАРОЛЬ',
    submitBtn: 'ОТПРАВИТЬ',
    cancelBtn: 'ОТМЕНА',
    passwordError: 'Ошибка при смене пароля',
    userAddTitle: 'РЕГИСТРАЦИЯ ПОЛЬЗОВАТЕЛЯ',
    userEditTitle: 'РЕДАКТИРОВАНИЕ ПОЛЬЗОВАТЕЛЯ',
    addOrderTitle: 'Новый заказ',
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
    actionsCol: 'ДЕЙСТВИЯ',
    editBtn: 'ИЗМЕНИТЬ',
    deleteBtn: 'УДАЛИТЬ',
    deleteServerError: 'Сервер отклонил запрос на удаление',
    yes: 'Да',
    no: 'Нет',
    loginCol: 'ЛОГИН',
    fioCol: 'ФИО',
    emailCol: 'EMAIL',
    birthdayCol: 'ДАТА',
    rolesCol: 'РОЛИ',
    deleteConfirm: 'Вы уверены, что хотите удалить',
    selfDeleteError: 'Вы не можете удалить самого себя!',
    loadUsersError: 'Не удалось получить данные пользователей',
    clientCol: 'КЛИЕНТ',
    adminCol: 'АДМИН',
    capacityCol: 'КОЛ-ВО МЕСТ',
    classCol: 'КЛАСС АПАРТАМЕНТОВ',
    daysCol: 'КОЛ-ВО ДНЕЙ',
    statusCol: 'СТАТУС',
    paidCol: 'ОПЛАТА',
    loadOrdersError: 'Ошибка загрузки заказов',
    deleteOrderConfirm: 'Удалить заказ №',
    processBtn: 'ОБРАБОТАТЬ',
    statusPending: 'В ОЖИДАНИИ',
    statusConfirmed: 'ПОДТВЕРЖДЕН',
    statusRejected: 'ОТКЛОНЕН',
    statusPaid: 'ОПЛАЧЕН',
    pendingOrders: 'Входящие заказы',
    confirmStatus: 'ПОДТВЕРДИТЬ',
    rejectStatus: 'ОТКЛОНИТЬ'
  },
  en: {
    authenticatedAs: 'Authenticated as:',
    logout: 'Logout',
    home: 'Home',
    users: 'Users',
    orders: 'Orders',
    addUser: 'Add user',
    loginTitle: 'System Login',
    loginLabel: 'LOGIN',
    passwordLabel: 'PASSWORD',
    loginBtn: 'Login',
    loginError: 'Invalid login or password',
    registrationTitle: 'Registration',
    noAccountText: 'No account?',
    passwordEditTitle: 'CHANGE PASSWORD',
    oldPasswordLabel: 'OLD PASSWORD',
    newPasswordLabel: 'NEW PASSWORD',
    submitBtn: 'SUBMIT',
    cancelBtn: 'CANCEL',
    passwordError: 'Error changing password',
    userAddTitle: 'USER REGISTRATION',
    userEditTitle: 'EDIT USER',
    addOrderTitle: 'New order',
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
    actionsCol: 'ACTIONS',
    editBtn: 'EDIT',
    deleteBtn: 'DELETE',
    deleteServerError: 'Server rejected deletion request',
    yes: 'Yes',
    no: 'No',
    loginCol: 'LOGIN',
    fioCol: 'FULL NAME',
    emailCol: 'EMAIL',
    birthdayCol: 'DATE',
    rolesCol: 'ROLES',
    deleteConfirm: 'Are you sure you want to delete',
    selfDeleteError: 'You cannot delete yourself!',
    loadUsersError: 'Failed to retrieve users from server',
    clientCol: 'CLIENT',
    adminCol: 'ADMIN',
    capacityCol: 'CAPACITY',
    classCol: 'APARTMENT CLASS',
    daysCol: 'STAY DAYS',
    statusCol: 'STATUS',
    paidCol: 'PAID',
    loadOrdersError: 'Failed to load orders',
    deleteOrderConfirm: 'Delete order #',
    processBtn: 'PROCESS',
    statusPending: 'PENDING',
    statusConfirmed: 'CONFIRMED',
    statusRejected: 'REJECTED',
    statusPaid: 'PAID',
    pendingOrders: 'Incoming Orders',
    confirmStatus: 'CONFIRM',
    rejectStatus: 'REJECT'
  }
};
